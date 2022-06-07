import fileinput
import doctest
import sys

tense_dict = {
    "past": "I",
    "present": "Kei te",
    "future": "Ka"
    }

past_verb_dict = {
    "went": "haere",
    "made": "hanga",
    "saw": "kite",
    "wanted": "hiahia",
    "called": "karanga",
    "asked": "p\u0101tai",
    "read": "p\u0101nui",
    "learnt": "ako"
}

present_verb_dict = {
    "going": "haere",
    "making": "hanga",
    "seeing": "kite",
    "wanting": "hiahia",
    "calling": "karanga",
    "asking": "p\u0101tai",
    "reading": "p\u0101nui",
    "learning": "ako"
}

future_verb_dict = {
    "go": "haere",
    "make": "hanga",
    "see": "kite",
    "want": "hiahia",
    "call": "karanga",
    "ask": "p\u0101tai",
    "read": "p\u0101nui",
    "learn": "ako"
    
}

incl_dict = {
    '(2 incl)': "t\u0101ua",
    "(3 incl)": "t\u0101tou",
    
    }

excl_speak_dict = {
    "(2 excl)": "k\u014Drua",
    "(3 excl)": "koutou",
    }

excl_list_dict = {
    "(2 excl)": "m\u0101ua",
    "(3 excl)": "m\u0101tou",
    }

no_speak_dict = {
    "(2 excl)": "r\u0101ua",
    "(3 excl)": "r\u0101tou"
    }

singular_dict = {
    "i": "au",
    "you": "koe",
    "he": "ia",
    "she": "ia",
    "him": "ia",
    "her": "ia"
    }

"""
    find_verb
    method to retrive maori translation of a verb passed as parameter
    returns maori translation as a string
    or returns invalid if translation for the given word is not in our dictionarie
"""
def find_verb(word):
    """
    >>> find_verb("learn")
    'ako'
    >>> find_verb("go")
    'haere'
    >>> find_verb("wanting")
    'hiahia'
    >>> find_verb("made")
    'hanga'
    >>> find_verb("saw")
    'kite'
    >>> find_verb("call")
    'karanga'
    >>> find_verb("asking")
    'p\u0101tai'
    >>> find_verb("reading")
    'p\u0101nui'
    >>> find_verb("coming")
    'invalid'
    """
    if word in past_verb_dict:
        return past_verb_dict[word]
    if word in present_verb_dict:
        return present_verb_dict[word]
    if word in future_verb_dict:
        return future_verb_dict[word]
    else:
        return "invalid"

"""
method find_tense(sentence)
finds the tense of an english sentence and returns the Maori translation for the tense
"""
def find_tense(sentence):
    """
    >>> find_tense("We (3 excl) are going")
    'Kei te'
    >>> find_tense("I am going")
    'Kei te'
    >>> find_tense("They (2 excl) are reading")
    'Kei te'
    >>> find_tense("You (2 incl) are reading")
    'Kei te'
    >>> find_tense("I went")
    'I'
    >>> find_tense("I will go")
    'Ka'
    >>> find_tense("I will read")
    'Ka'
    >>> find_tense("I read")
    'I'
    """
    if sentence.split()[-1] in past_verb_dict and sentence.split()[-1] != "read":
        return tense_dict["past"]
    if sentence.split()[-1] in present_verb_dict:
        return tense_dict["present"]
    if sentence.split()[-1] in future_verb_dict and sentence.split()[-1] != "read":
        return tense_dict["future"]
    #special case for determing tense when read is verb as has same spelling for past and future
    #looks at the word before read to determine it's tense
    if sentence.split()[-1] == "read":
        if sentence.split()[-2] == "will":
            return tense_dict["future"]
        else:
            return tense_dict["past"]
        
"""
Finds the subjects of a sentence (eg the speaker and/or listener)
returns the Maori translation for defining the subject
"""
def find_subject(sentence):
    """
    >>> find_subject("we (3 excl) are going")
    'm\u0101tou'
    >>> find_subject("i am going")
    'au'
    >>> find_subject("they (2 excl) are reading")
    'r\u0101ua'
    >>> find_subject("you (2 incl) are reading")
    'k\u014Drua'
    >>> find_subject("i went")
    'au'
    >>> find_subject("i will go")
    'au'
    >>> find_subject("we (2 excl) are going")
    'm\u0101ua'
    >>> find_subject("we (2 incl) are going")
    't\u0101ua'
    >>> find_subject("we (3 incl) are going")
    't\u0101tou'
    >>> find_subject("they (3 excl) are going")
    'r\u0101tou'
    """
    if "(" in sentence:
        excl_rule = sentence[sentence.find('(') + 1 : sentence.find(')')]
        excl_rule = excl_rule.split()
        sentence = sentence.split()
        #If it excludes either speaker or listener or both
        if excl_rule[-1] == "excl":
            
            if excl_rule[0] == "2":
                #Sentence starts with WE
                if sentence[0] == "we":
                    return excl_list_dict["(2 excl)"]
                #Sentenc starts ith YOU
                if sentence[0] == "you":
                    return excl_speak_dict["(2 excl)"]
                #Sentence starts with They
                if sentence[0] == "they":
                    return no_speak_dict["(2 excl)"]
            else:
                #Sentence starts with WE
                if sentence[0] == "we":
                    return excl_list_dict["(3 excl)"]
                #Sentenc starts ith YOU
                if sentence[0] == "you":
                    return excl_speak_dict["(3 excl)"]
                #Sentence starts with They
                if sentence[0] == "they":
                    return no_speak_dict["(3 excl)"]
           
        #If it include listener
        if excl_rule[-1] == "incl":
            
            if excl_rule[0] == "2":
                #Sentence starts with WE
                if sentence[0] == "we":
                    return incl_dict["(2 incl)"]
                #Sentence start with YOU
                if sentence[0] == "you":
                    return excl_speak_dict["(2 excl)"]
            else:
                #Sentece starts with WE
                if sentence[0] == "we":
                    return incl_dict["(3 incl)"]
                #Sentence starts with YOU
                if sentence[0] == "you":
                    return excl_speak_dict["(3 excl)"]
    
    sentence = sentence.split()
    #if there's no exclusion then check that the sentence refers to the speaker
    if sentence[0] in singular_dict:
            return singular_dict[sentence[0]]
    
    else:
            return "invalid"
    
    
#Cannot interpret special characters such as 'a' with macron in source code
#Relevant for 'Maori' below
"""
The lookup_and_print function prints the Maori translation
The function will throw an exception if the word is not found in the dictionary.
"""

"""
def lookup_and_print(english_word, word_dict):
    print(word_dict[english_word])
"""

"""
The main method adds the dictionaries to a list which it uses to lookup_and_print and print and print(if the given word exists) each word in the lookup_and_print function
"""
def main(sentence):
    """
    >>> main("We (3 excl) are going")
    Kei te haere m\u0101tou
    >>> main("I am going")
    Kei te haere au
    >>> main("They (2 excl) are reading")
    Kei te p\u0101nui r\u0101ua
    >>> main("You (2 incl) are reading")
    Kei te p\u0101nui k\u014Drua
    >>> main("I went")
    I haere au
    >>> main("I will go")
    Ka haere au
    >>> main("gibberish")
    invalid sentence
    >>> main("We are coming")
    unknown verb "coming"
    >>> main("12353")
    invalid sentence
    >>> main("@#$$$")
    invalid sentence
    >>> main("You (2 incl) are fishing")
    unknown verb "fishing"
    >>> main("We (5 excl) are learning")
    Kei te ako m\u0101tou
    >>> main("We")
    invalid sentence
    """
   
    
    sentence = sentence.lower()
    subject = find_subject(sentence)
    #If there's one word or less then it's not a sentence
    if len(sentence.split()) <= 1:
        print("invalid sentence")
    #Check if the verb is a known verb   
    elif find_verb(sentence.split()[-1]) == "invalid":
        print('unknown verb "' + sentence.split()[-1] +'"')
    #check if there is a subject of the sentence  
    elif subject == "invalid":
        print("invalid sentence")
            
                    
    else:
        print(find_tense(sentence) + " " + find_verb(sentence.split()[-1]) + " " + find_subject(sentence))



if __name__ == '__main__':
    if (len(sys.argv) > 1 and sys.argv[1] == '-t'):
        doctest.testmod()
    else:
        for line in fileinput.input():
            main(line)
"""
for word in split_sentence:
    if word in past_verb_dict
"""


"""
if(string[-1] in present_verb_dict):
    print("present")
    
if(string[-1] in past_verb_dict):
    print("past")
    
if(string[-1] in future_verb_dict):
    print("future")
"""
