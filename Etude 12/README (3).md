Student Ids: 
Liam Lennon-Flynn - 4239344
Ben Stacey - 2157359
James Pusey - 5738464
Christian Gehrmann - 9355607

Instructions:
- Run the program
- Will then prompt user for filepath to bin file, precision of input, filepath to write output to, and output precision
- for precision please use "s" for single and "d" for double

Code Steps:
- prompts user for input file, output file, precision of input (single or double), precision of output (single or double)
- depedning on answer reads inputfile as a 32 bit or 64 bit binary numbers in IBM format
- determines neccasary components of IBM floating point from bin num
- converts these to neccasary components of IEE floating point
- contructs IEE floating point
- converts to string rep (binary) and writes it

Who did what:
Liam and Ben worked out reading input and output of bin files. Created a structed commented pseudocode on how to convert IBM IEEE and remake IEEE numbers. Meanwhile Christian and James using reading/writing functions and structured outline implemented, tested and debugged code to contruct IMB/IEE numbers and remaking IEEE. 

Resources:
- All members used FloatingPointDocs.zip
- James also consulted prior notes from Computer Systems class on 64 bit floating point representations.