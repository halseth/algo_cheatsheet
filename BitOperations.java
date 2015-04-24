class BitOperations {
  void bitOperations(int a, int b) {
      int and = a & b; // and, 1 iff both bits are 1, 3 & 5 == 1
      int or = a | b; // or, 0 iff both bits are 0, 3 | 5 == 7
      int xor = a ^ b; // xor, 1 iff both bits are different, 3 ^ 5 == 6
      int not = ~a; // not, 1 iff the bit is 0, ~3 == -4 (negative because of the negated sign bit (two's complement representation))
      int ls = a << b; // left shift, shift the bits of a b positions to the left (0 is shifted in), 3 << 5 == 96
      int rs = a >> b; // right shift, shift the bits of a b positions to the right (value of the sign bit is shifted in), -96 >> 4 == -6
      int urs = a >>> b; // unsigned right shift, shift the bits of a b positions to the right (0 is shifted in), -96 >>> 28 == 15
   }
}
