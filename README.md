## Huffman Compressor

This program takes a user-inputted text file and compresses it into an .MZIP file.

How it works: The frequency of each character in the text file is counted and stored into a frequency array. The array is then formed into a priority queue, where the characters with the lowest frequency are at the front of the queue. Elements are popped off of the queue to create a Huffman tree represented by integers and brackets, and the encoded text (represented by a series of 0s and 1s) is printed out into a new file.

A sample text file Frankenstein.txt can be used to test the program.
