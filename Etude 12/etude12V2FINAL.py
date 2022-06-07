
"""
Student Ids: 
Liam Lennon-Flynn - 4239344
Ben Stacey - 2157359
James Pusey - 5738464
Christian Gehrmann - 9355607
"""
import struct
import sys
import math

#determines neccasary components of IBM floating point from typeVar (32 64) bit ibm representation
def convertIBM(ibm, typeVar):
    if (typeVar=="d"):
        significantBitsS=[63,56,[2,9]]
    elif(typeVar=="s"):
        significantBitsS=[31,23,[2,9]]

    # Retrieve the sign
    sign = (ibm >> significantBitsS[0])

    # Retrieve the exponent
    binExponent = bin((ibm >> significantBitsS[1]))

    string_binExponent = "0b" + binExponent[significantBitsS[2][0]+sign:significantBitsS[2][1]+sign]

    # Retrieve the fraction
    binFraction = bin(ibm)
    string_binFraction = "0b" + binFraction[9+sign:]

    exponent = int(bin(int(string_binExponent, 2)), 2)

    
    if(typeVar=="s"):
        # IEEE uses 2**exponent, so it converts the exponent for IEEE format
        exponent -= 64
        exponent *= 4
        # print("TEST1",sign, exponent, string_binFraction) #testing
        return makeIEEE(sign, exponent, string_binFraction, typeVar)
    elif (typeVar=="d"):
        # print("TEST1",sign, exponent, string_binFraction) #testing
        return makeIEEE(sign, exponent, string_binFraction, typeVar)
    

#determines neccasary components of IEE floating point from typeVar (32 64) bit ibm representation
def makeIEEE(sign, exponent, string_fraction, typeVar):
    if (typeVar=="d"):
        IEEEexponent=1023

        # Checks if exponent is infinite
        string_exponent = str(bin(exponent)) + string_fraction[2:6]
        if int(string_exponent, 2) == 2047:
            return (-1)**sign * math.inf

        # IEEE uses 2**exponent, so it converts the exponent for IEEE format
        exponent -= 64
        exponent *= 4

    elif(typeVar=="s"):
        IEEEexponent=127
    
    # Floating point is 0
    if exponent < 0:
        return 0
    exponent += IEEEexponent  # The IEEE exponent
    # Gets the mantissa by normalising the fraction
    temp = string_fraction
    string_mantissa = "0." + temp[2:]
    # Normalising the fraction
    while string_mantissa[0] != "1" and exponent > 0:
        string_mantissa = string_mantissa[2] + "." + string_mantissa[3:] + "0"
        exponent -= 1

    # The floating point is normal, otherwise it is zero
    if exponent > 0:
        true_fraction = getMatissa(string_mantissa)  # The value of the mantissa
        # print("TEST2",sign, exponent, true_fraction) #testing
        return convertIEEE(sign, exponent, true_fraction, typeVar)
    else:
        return 0

#Recieves floating point components determined above and returns IEEE float value
def convertIEEE(sign, exponent, fraction, typeVar):
    if (typeVar=="s"):
        significantBitsS=[255,127]
    elif(typeVar=="d"):
        significantBitsS=[2047,1023]
        
    if exponent == significantBitsS[0] and fraction != 0:
        # NaN
        return 0
    elif exponent > significantBitsS[0]:
        # infinite
        return (-1)**sign * math.inf
    elif 0 < exponent < significantBitsS[0]:
        # normal
        return (-1) ** sign * 2 ** (exponent - significantBitsS[1]) * fraction
    else:
        return 0

#determines the matissa value from binary number
def getMatissa(string_fraction):
    result = 0.0
    length = len(string_fraction)
    i = 0
    n = 0

    while i < length:
        if string_fraction[i] == ".":
            i += 1
        if string_fraction[i] == "1":
            result += (1 * 2 ** n)
        i += 1
        n -= 1

    return result

"""
Reads input file, and depending on input prescision(varType) and output
precision f, converts from IMB360 to IEEE754 floating point num

"""

def read(in_file, out_file, f,typeVar):
    length = 0
    mult=1
    if typeVar=="d":
        mult*=2
    try:
        with open(in_file, "rb") as in_stream:
            with open(out_file, "wb") as out:
                while True:
                    chunk = in_stream.read(4*mult)
                    # print("chunk:", chunk) testing
                    if chunk:
                        length += 1
                        B = ""
                        for b in chunk:
                            if b == 0:
                                B += "00"
                            else:
                                B += hex(b)[2:]
 
                        # Treats input as big endian
                        reverse_B = ""
                        i = len(B) - 1
                        while i >= 0:
                            reverse_B += B[i - 1:i + 1]
                            i -= 2
                        # print("B: ", B) testing
                        if f == "s":
                            f_point = convertIBM(int(B, 16), typeVar)
                            # print("F:", f_point) testing
                            result = struct.pack('f', f_point) #converts to string rep and writes
                            # print("unpack:", struct.unpack('f', result)) #testing
                        else:
                            f_point = convertIBM(int(B, 16), typeVar) #converts to string rep
                            # print("F:", f_point) #testing
                            result = struct.pack('d', f_point)
                            # print("unpack:", struct.unpack('d', result)) #tesing

                        out.write(result)
                    else:
                        break
    except FileNotFoundError:
        print("Input file does not exist")

    if length > 0:
        if (typeVar=="s"):
            print("Successfully converted", length, "of IBM/360 floating point numbers to IEEE 754 floating point numbers "
                                                "from", in_file, "to", out_file)
        elif(typeVar=="d"):
            print("Successfully converted", length,
              "IBM/360 floating point numbers to IEEE 754 floating point numbers from",
              in_file, "to", out_file)

# Main, reads arguments and performs conversion.
def main():
    try:
        version = sys.version_info[0]

        print("Please enter an input file path!")
        inputPath = raw_input() if version==2 else input()
        print("And the precision?")
        inputPrecision = raw_input() if version==2 else input()
        print("Please enter an output file path")
        outputPath = raw_input() if version==2 else input()
        print("And the precision?")
        outputPrecision = raw_input() if version==2 else input()

        if inputPrecision  not in "sd":
            print("Invalid argument for input precision")
        elif outputPrecision not in "sd":
            print("Invalid argument for output precision")
        else:
            if inputPrecision == "s":
                read(inputPath, outputPath, outputPrecision, inputPrecision)

            elif inputPrecision == "d":
                read(inputPath, outputPath, outputPrecision, inputPrecision)

    except EOFError:
        print("error")

#used for testing
def readtest(in_file, f,typeVar):
    length = 0
    mult=1
    if typeVar=="d":
        mult*=2
    try:
        with open(in_file, "rb") as in_stream:
            while True:
                chunk = in_stream.read(4*mult)
                print("chunk:", chunk)
                if chunk:
                    length += 1
                    B = ""
                    for b in chunk:
                        if b == 0:
                            B += "00"
                        else:
                            B += hex(b)[2:]

                    # Treats input as big endian
                    reverse_B = ""
                    i = len(B) - 1
                    while i >= 0:
                        reverse_B += B[i - 1:i + 1]
                        i -= 2
                    print("B: ", B)
                else:
                    break
    except FileNotFoundError:
        print("Couldn't read")
main()
#readtest("/Users/pusejh19/Desktop/Etude12/Etude12/Final/Test.bin", "s","s")

"""

Test paths for debugging
/Users/pusejh19/Desktop/Etude12/V1/testIBMSingle.bin
/Users/pusejh19/Desktop/Etude12/V1/testIBMDouble.bin
/Users/pusejh19/Desktop/Etude12/Etude12/Final/Test.bin

"""