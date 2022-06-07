"""
Group Members
Liam Lennon-Flynn - 4239344
Ben Stacey - 2157359
Harry Pusey - 5738464
Christian Gehrmann - 9355607
"""

import string
#establish list of lowercase alphabet list


#function to check if we add letter to the end of current sequence if it will reppear a sequence that already currently exists
#
def check(target,string):
    target=string[len(string)-1]+target
    for i in range(0,len(string)):
        if (target == string[i:i+2]):
            return ""
    return target[1]

#fuction that loops through previously added value (loopNum) to the end of the alphabet
def loop(string, number, loopNum):
    #gets position of in alphabet of last added char
    for alph in range(0,26):
        if (alphabet[alph]==string[-1]):
            loopNum=alph

    #iterates through last added chars postion in alphabet to end
    for j in range(loopNum,number):
        target = alphabet[j]
        if (check(target, string)!=""):
            string+=check(target,string)
            loop(string,number,loopNum)
            break
    return string

if __name__ == "__main__":
#Check every combination of sequences that can be added by going through a +a...z, b + a...z, c + a....z
    alphabet= list(string.ascii_lowercase)
    while(True):
        try:
            number= int(input("What is the alphabet size(1-26) enter 0 to quit: "))
            if (number==0):
                break
            
            string=''
            target=''

            #Establish initial repeat sequences ie. aa bb cc
            for i in range (0,number):
                string+=alphabet[i]*2
            #iterate through every number
            for i in range(0,26):
                for i in range (0,number):
                    target = alphabet[i]
                    string+=check(target,string)
                    string=loop(string, number, 0)
            print("String Generated: ", string, "\nString Length: ", len(string),"\n")
        except:
            print("Error detected please input a value from 1-26 or 0 to exit")
    print("System Exited")