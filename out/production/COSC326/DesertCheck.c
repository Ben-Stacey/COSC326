/* Desert Crossing in C. Translated from Pascal by p2c. */
/* Pascal version written by Chris Handley */
/* Finer details of translation,
	Improved error-reporting,
    Desert width as command-line argument,
    Counting of physical cans by Matthew Jenkin */
/* Tolerance adjustment by Kirk Alexander */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>
#include <math.h>



/* Used to prevent floating-point errors when comparing distances/litres */
#define tolerance 0.001
#define eq(a, b) (((a) > ((b) - tolerance)) && ((a) < (b) + tolerance))
#define gt(a, b) ((a) > ((b) + tolerance))
#define lt(a, b) ((a) < ((b) - tolerance))



/* Base stats for the situation */
#define DefaultDesert   2413.0
#define KMperLitre      12.0
#define TankVol         60.0
#define MaxCans         4
#define CanVol			   20.0
#define BaseFuel        100000.0
#define BaseCans        10000



/* Useful constants to make code more readable */
#define FWD     1
#define BCK    -1

#define TRUE    1
#define FALSE   0



/* Data type for a linked-list of fuel-dumps that have a known location
   (distance from base), litres of fuel, and number of cans. */
typedef struct _dump {
  double dist, fuel;
  int cans;
  struct _dump* next;
} dump;



/* Global variables (as they are changed/read in various places but have
   only one value at a time). */
double DesertWidth;
dump* base;
double tank, fuel, dist, lastStop;
int cans, crossed, count, visitedLastStop;
char iarray[2];
double qarray[2];




/* Finds how much fuel is dumped at the current location (returning 0 if
   there is no dump at the current location). */
double DumpVal(void) {
  dump* p = base;

  while ((p != NULL) && (!eq(p->dist, dist))) p = p->next;
  if (p == NULL) return 0;
  return p->fuel;
}




/* Error condition - indicates what went wrong, where it occured,
   relevant values associated. */
void Error(char* errmsg, double val) {
  int temp;

  printf("Error at %7.3fkms:\n\t%s %7.3f\n", dist, errmsg, val);
  printf("\ttank = %5.3f cans = %6.3f dump = %7.3f\n", tank, fuel, DumpVal());
  srand((unsigned int)time(NULL));
  temp = rand() % 4;
  if (temp == 0) printf("You are subsequently eaten by a sand-worm.\n");
  if (temp == 1) printf("Tuscan raiders steal your tires.\n");
  if (temp == 2) printf("You imagine you see Las Vegas in the distance.\n");
  if (temp == 3) printf("You settle down to work on your tan.\n");
  exit(EXIT_FAILURE);
}




/* Finds a node in the linked list that represents the current dump point
   (or creates a new one if none exists). */
dump* FindNode(void) {
  dump* p;
  dump* q;

  p = base;
  while ((p != NULL) && (gt(dist, p->dist))) {
    q = p;
    p = p->next;
  }
  if ((p != NULL) && (eq(dist, p->dist))) return p;
  else {
    p = (dump*)malloc(sizeof(dump));
    p->dist = dist;
    p->fuel = 0.0;
    p->next = q->next;
    q->next = p;
    return p;
  }
}




/* Puts fuel into the tank from what is available at the current location. */
void DoTank(double quantity, int trip) {
  dump* p;

  if (eq(dist, 0.0)) printf("Trip no %2d\n", trip);
  if (gt(tank + quantity, TankVol))
    Error("Attempt to overfill tank, amount = ", tank + quantity);
  p = FindNode();
  if (lt(p->fuel, quantity))
    Error("Not enough fuel at this dump, amount = ", p->fuel);
  printf("  At %7.2fkm putting  %6.2f litres in tank\n", dist, quantity);
  p->fuel -= quantity;
  tank += quantity;
}




/* Collects fuel from current location and loads it onto the vehicle.
   If there is sufficient canspace on the vehicle, fuel is poured directly.
   If not, whole cans are added until there are 4 on the truck, or there
   is less than a can to be picked up. If the rest can be either picked up
   in part of a can, or poured into the tank, it is, otherwise an error is
   returned. */
void DoCans(double quantity) {
  dump* p;
  double space, spillage;

  if (gt(fuel + quantity, MaxCans * CanVol))
    Error("Attempt to load too many cans, total = ", fuel + quantity);
  p = FindNode();
  if (lt(p->fuel, quantity))
    Error("Not enough fuel at this dump, request = ", quantity);
  printf("  At %7.2fkm putting  %6.2f litres in cans\n", dist, quantity);
  space = ((double)cans * CanVol) - fuel;
  if (!lt(space, quantity)) {
    /* Straight transfer into cans already on vehicle. */
    p->fuel -= quantity;
    fuel += quantity;
  } else {
    while ((!lt(quantity, CanVol)) && (cans < MaxCans)) {
      /* Pick up whole cans */
      p->fuel -= CanVol;
      p->cans--;
      fuel += CanVol;
      cans++;
      quantity -= CanVol;
    }
    if (cans < MaxCans) {
      /* pick up part of a can */
      p->fuel -= quantity;
      p->cans--;
      fuel += quantity;
      cans++;
    } else {
      space = (cans * CanVol) - fuel;
      if (!lt(space, quantity)) {
        p->fuel -= quantity;
        fuel += quantity;
      } else Error("Cannot take this amount, excess = ", quantity - space);
    }
    spillage = p->fuel - (p->cans * CanVol);
    if (gt(spillage, 0.0)) {
      printf("%5.2f litres of fuel spilled\n", spillage);
      p->fuel -= spillage;
    }
  }
}




/* Move forward or backwards in the desert (dir is 1 for forwards,
   -1 for backwards). */
void DoDrive(int dir, double quantity) {
  double fuelcost;

  dist += dir * quantity;
  fuelcost = quantity / KMperLitre;
  if (gt(fuelcost, tank + fuel))
    Error("Out of petrol, cost = ", fuelcost);
  if (gt(dist, DesertWidth)) Error(" Overshot end of desert, position = ",
                                   dist);
  if (lt(dist, 0.0)) Error("Overshot base, position = ", dist);
  if (eq(dist, DesertWidth)) {
    printf("\tCONGRATULATIONS - you have reached the end of the desert!\n");
    crossed = TRUE;
  }
  if ((visitedLastStop == FALSE) && eq(dist, lastStop)) visitedLastStop = TRUE;
  tank -= fuelcost;
  if (lt(tank, 0.0)) {
    fuel += tank;
    tank = 0.0;
  }
}




/* Drops a specified amount of fuel at the current location. If there
   are sufficient empty cans at the location, they are used. Once the
   space is exhausted, full cans are transferred from the vehicle to
   the drop-point. If more needs to be dropped (e.g. 1/2 a can),
   various details are examined:
     1) Can that amount be dropped without disrupting the rest of the
        fuel carried by the vehicle?
     2) If not, can the leftover be poured into the tank?
     3) If not, the leftover is considered wasted (poured out on the
        ground). */
void DoDrop(double quantity) {
  dump* p;
  double space, spillage;
  int canskept;

  if (gt(quantity, fuel)) Error("Cannot drop this amount, request = ",
                                quantity);
  p = FindNode();
  space = (CanVol * (double)p->cans) - p->fuel;
  printf("  At %7.2fkm dropping %6.2f litres\n", dist, quantity);
  if (!lt(space, quantity)) {
    /* Enough empty can space at the drop-point. */
    p->fuel += quantity;
    fuel -= quantity;
  } else {
    /* Pour what can be transferred. */
    p->fuel += space;
    fuel -= space;
    quantity -= space;
    while (!lt(quantity, CanVol)) {
      /* Drop whole cans. */
      p->fuel += CanVol;
      p->cans++;
      fuel -= CanVol;
      cans--;
      quantity -= CanVol;
    }
    if (!eq(quantity, 0.0)) {
      spillage = fuel - quantity;
      canskept = (int)ceil(spillage / CanVol);
      while (!lt(spillage, CanVol)) spillage -= CanVol;
      if ((!eq(spillage, 0.0)) && (canskept == cans)) {
        printf("\t%5.2f litres", spillage);
        if (!gt(tank + spillage, TankVol)) {
          tank += spillage;
          fuel -= spillage;
          printf(" added to tank\n");
        } else if (lt(tank, TankVol)) {
          fuel -= TankVol - tank;
          spillage -= TankVol - tank;
          printf(" to redistribute, %5.2f litres", TankVol - tank);
          printf(" added to tank, %5.2f litres spilled\n", spillage);
          tank = TankVol;
          fuel -= spillage;
        }
      }
      p->fuel += quantity;
      p->cans++;
      fuel -= quantity;
      cans--;
    }
  }
}




/* Checks to ensure expected character is next non-whitespace input. */
static void Check(char which) {
  char c;

  do {
    c = getchar();
    if (c == '\n') c = ' ';
  } while (c == ' ');
  if (c == which) return;
  printf("Expected '%c' found '%c'\n", which, c);
  exit(EXIT_FAILURE);
}




/* Finds the instructions (and related quantities) to be repeatedly
   performed. */
void DoMult(double quantity) {
  count = 2 * (int)quantity;
  Check('(');
  do {
    iarray[0] = getchar();
    if (iarray[0] == '\n') iarray[0] = ' ';
  } while (iarray[0] == ' ');
  scanf("%lg", qarray);
  do {
    iarray[1] = getchar();
    if (iarray[1] == '\n') iarray[1] = ' ';
  } while (iarray[1] == ' ');
  scanf("%lg", &qarray[1]);
  Check(')');
}




/* Display amount of fuel (and number of cans) at each drop-point. */
void DumpDumps(char* msg) {
  dump* p;
  double sum = -base->fuel;

  printf("Dumping Dumps: %s\n", msg);
  p = base;
  while (p != NULL) {
    if ((visitedLastStop == FALSE) && eq(p->dist, lastStop)) {
      p = p->next;
      continue;
    }
    printf("  Dump at %7.2f: %6.2f litres in %3d cans\n",
           p->dist, p->fuel, p->cans);
    sum += p->fuel;
    p = p->next;
  }
  printf("  Total fuel in the desert: %6.2f litres\n", sum);
}




/* Gives a summary of the solution (whether it works, etc.) and cleans up
   the linked list of drop-points. */
void DisplayResults(int crossed, double claim) {
  int first;
  double used;
  dump* p;

  used = BaseFuel - base->fuel;
  p = base->next;
  first = TRUE;
  while (p != NULL) {
    if (!eq(p->fuel, 0.0)) {
      if (first == TRUE) {
        printf("\nYou left the following dumps in the desert:\n");
        first = FALSE;
      }
      printf("%8.2f litres at %7.2f km\n", p->fuel, p->dist);
    }
    free(base);
    base = p;
    p = p->next;
  }
  if (first == FALSE) printf("\n");
  if (!eq(used, claim)) {
    printf("Your claim of %4.2f litres is wrong", claim);
    printf(" -- check your arithmetic\n\n");
  }
  if (crossed == FALSE) {
    printf("You used %4.2f litres of fuel,", used);
    printf(" but did not cross the desert! ******************************\n");
  } else {
    printf("You used %4.2f litres of fuel to cross %4.0f", used, DesertWidth);
    printf(" km of desert and return safely\n");
  }
  printf("\nDone\n");
}




int main(int argc, char** argv) {
  int done, trip, c;
  char instruction;
  double quantity;

  /* Width of desert may be passed as a command-line argument */
  if (argc == 2) DesertWidth = (double)atoi(argv[1]);
  else DesertWidth = DefaultDesert;
  /* Display first line (typically name of solution) */
  do {
    c = getchar();
    printf("%c", c);
  } while (c != '\n');
  printf("\n\n");
  /* Initialise linked list of dump points */
  base = (dump*)malloc(sizeof(dump));
  base->dist = 0.0;
  base->fuel = BaseFuel;
  base->cans = BaseCans;
  base->next = NULL;
  base->next = (dump*)malloc(sizeof(dump));
  lastStop = DesertWidth - ((TankVol + (MaxCans * CanVol)) * KMperLitre / 2.0);
  visitedLastStop = FALSE;
  base->next->dist = lastStop;
  base->next->fuel = 0.0;
  base->next->cans = 0;
  base->next->next = NULL;
  crossed = FALSE;
  tank = 0.0;
  fuel = 0.0;
  cans = 0;
  dist = 0.0;
  trip = 0;
  done = FALSE;
  do {
    trip++;
    count = 0;
    while ((instruction != '\n') && (instruction != EOF)) {
      if (count > 0) {
        instruction = iarray[count & 1];
        quantity = qarray[count & 1];
        count--;
      } else {
        do {
          instruction = getchar();
        } while (instruction == ' ');
        scanf("%lg", &quantity);
      }
      instruction = toupper(instruction);
      if (instruction == '#') done = TRUE;
      else if (instruction == 'T') DoTank(quantity, trip);
      else if (instruction == 'C') DoCans(quantity);
      else if (instruction == 'F') DoDrive(FWD, quantity);
      else if (instruction == 'B') DoDrive(BCK, quantity);
      else if (instruction == 'D') DoDrop(quantity);
      else if (instruction == '*') DoMult(quantity);
    }
    if (gt(dist, 0.0)) Error("You did not make it back to base, position = ",
                             dist);
    if (gt(tank + fuel, 0.0)) {
      printf("You have arrived at base with %5.2f", tank + fuel);
      printf(" litres of fuel left\n");
    }
    if (done == FALSE) DumpDumps("After trip");
    printf("\n");
    instruction = ' ';
  } while (done == FALSE);
  DisplayResults(crossed, quantity);
  return EXIT_SUCCESS;
}
