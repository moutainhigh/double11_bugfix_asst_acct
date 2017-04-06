var Security = {};

(function() {

// The Central Randomizer 1.3 (C) 1997 by Paul Houle (houle@msc.cornell.edu)
// See:&nbsp;http://www.msc.cornell.edu/~houle/javascript/randomizer.html
rnd.today=new Date();
rnd.seed=rnd.today.getTime() * Math.random();
function rnd() {
rnd.seed = (rnd.seed*9301+49297) % 233280;
return rnd.seed/(233280.0);
};
function rand(number) {
return Math.ceil(rnd()*number);
};
// end central randomizer. -->

function randomB(number) {
  var p = [];
  for (var i = 0; i < number; i++) {
    p[i] = Math.floor( rnd() * 256 );
  }
  return p;
};

/////////////
// AES
/////////////

/* rijndael.js      Rijndael Reference Implementation

    This is a modified version of the software described below,
    produced in September 2003 by John Walker for use in the
    JavsScrypt browser-based encryption package.  The principal
    changes are replacing the original getRandomBytes function with
    one which calls our pseudorandom generator (which must
    be instantiated and seeded before the first call on getRandomBytes),
    and changing keySizeInBits to 256.  Some code not required by the
    JavsScrypt application has been commented out.  Please see
    http://www.fourmilab.ch/javascrypt/ for further information on
    JavaScrypt.
    
    The following is the original copyright and application
    information.

   Copyright (c) 2001 Fritz Schneider
 
 This software is provided as-is, without express or implied warranty.  
 Permission to use, copy, modify, distribute or sell this software, with or
 without fee, for any purpose and by any individual or organization, is hereby
 granted, provided that the above copyright notice and this paragraph appear 
 in all copies. Distribution as a part of an application or binary must
 include the above copyright notice in the documentation and/or other materials
 provided with the application or distribution.

   As the above disclaimer notes, you are free to use this code however you
   want. However, I would request that you send me an email 
   (fritz /at/ cs /dot/ ucsd /dot/ edu) to say hi if you find this code useful
   or instructional. Seeing that people are using the code acts as 
   encouragement for me to continue development. If you *really* want to thank
   me you can buy the book I wrote with Thomas Powell, _JavaScript:
   _The_Complete_Reference_ :)

   This code is an UNOPTIMIZED REFERENCE implementation of Rijndael. 
   If there is sufficient interest I can write an optimized (word-based, 
   table-driven) version, although you might want to consider using a 
   compiled language if speed is critical to your application. As it stands,
   one run of the monte carlo test (10,000 encryptions) can take up to 
   several minutes, depending upon your processor. You shouldn't expect more
   than a few kilobytes per second in throughput.

   Also note that there is very little error checking in these functions. 
   Doing proper error checking is always a good idea, but the ideal 
   implementation (using the instanceof operator and exceptions) requires
   IE5+/NS6+, and I've chosen to implement this code so that it is compatible
   with IE4/NS4. 

   And finally, because JavaScript doesn't have an explicit byte/char data 
   type (although JavaScript 2.0 most likely will), when I refer to "byte" 
   in this code I generally mean "32 bit integer with value in the interval 
   [0,255]" which I treat as a byte.

   See http://www-cse.ucsd.edu/~fritz/rijndael.html for more documentation
   of the (very simple) API provided by this code.

                                               Fritz Schneider
                                               fritz at cs.ucsd.edu
 
*/


// Rijndael parameters --  Valid values are 128, 192, or 256

var keySizeInBits = 256;
var blockSizeInBits = 128;

//
// Note: in the following code the two dimensional arrays are indexed as
//       you would probably expect, as array[row][column]. The state arrays
//       are 2d arrays of the form state[4][Nb].


// The number of rounds for the cipher, indexed by [Nk][Nb]
var roundsArray = [ ,,,,[,,,,10,, 12,, 14],, 
                        [,,,,12,, 12,, 14],, 
                        [,,,,14,, 14,, 14] ];

// The number of bytes to shift by in shiftRow, indexed by [Nb][row]
var shiftOffsets = [ ,,,,[,1, 2, 3],,[,1, 2, 3],,[,1, 3, 4] ];

// The round constants used in subkey expansion
var Rcon = [ 
0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 
0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 
0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 
0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 
0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91 ];

// Precomputed lookup table for the SBox
var SBox = [
 99, 124, 119, 123, 242, 107, 111, 197,  48,   1, 103,  43, 254, 215, 171, 
118, 202, 130, 201, 125, 250,  89,  71, 240, 173, 212, 162, 175, 156, 164, 
114, 192, 183, 253, 147,  38,  54,  63, 247, 204,  52, 165, 229, 241, 113, 
216,  49,  21,   4, 199,  35, 195,  24, 150,   5, 154,   7,  18, 128, 226, 
235,  39, 178, 117,   9, 131,  44,  26,  27, 110,  90, 160,  82,  59, 214, 
179,  41, 227,  47, 132,  83, 209,   0, 237,  32, 252, 177,  91, 106, 203, 
190,  57,  74,  76,  88, 207, 208, 239, 170, 251,  67,  77,  51, 133,  69, 
249,   2, 127,  80,  60, 159, 168,  81, 163,  64, 143, 146, 157,  56, 245, 
188, 182, 218,  33,  16, 255, 243, 210, 205,  12,  19, 236,  95, 151,  68,  
23,  196, 167, 126,  61, 100,  93,  25, 115,  96, 129,  79, 220,  34,  42, 
144, 136,  70, 238, 184,  20, 222,  94,  11, 219, 224,  50,  58,  10,  73,
  6,  36,  92, 194, 211, 172,  98, 145, 149, 228, 121, 231, 200,  55, 109, 
141, 213,  78, 169, 108,  86, 244, 234, 101, 122, 174,   8, 186, 120,  37,  
 46,  28, 166, 180, 198, 232, 221, 116,  31,  75, 189, 139, 138, 112,  62, 
181, 102,  72,   3, 246,  14,  97,  53,  87, 185, 134, 193,  29, 158, 225,
248, 152,  17, 105, 217, 142, 148, 155,  30, 135, 233, 206,  85,  40, 223,
140, 161, 137,  13, 191, 230,  66, 104,  65, 153,  45,  15, 176,  84, 187,  
 22 ];

// Precomputed lookup table for the inverse SBox
var SBoxInverse = [
 82,   9, 106, 213,  48,  54, 165,  56, 191,  64, 163, 158, 129, 243, 215, 
251, 124, 227,  57, 130, 155,  47, 255, 135,  52, 142,  67,  68, 196, 222, 
233, 203,  84, 123, 148,  50, 166, 194,  35,  61, 238,  76, 149,  11,  66, 
250, 195,  78,   8,  46, 161, 102,  40, 217,  36, 178, 118,  91, 162,  73, 
109, 139, 209,  37, 114, 248, 246, 100, 134, 104, 152,  22, 212, 164,  92, 
204,  93, 101, 182, 146, 108, 112,  72,  80, 253, 237, 185, 218,  94,  21,  
 70,  87, 167, 141, 157, 132, 144, 216, 171,   0, 140, 188, 211,  10, 247, 
228,  88,   5, 184, 179,  69,   6, 208,  44,  30, 143, 202,  63,  15,   2, 
193, 175, 189,   3,   1,  19, 138, 107,  58, 145,  17,  65,  79, 103, 220, 
234, 151, 242, 207, 206, 240, 180, 230, 115, 150, 172, 116,  34, 231, 173,
 53, 133, 226, 249,  55, 232,  28, 117, 223, 110,  71, 241,  26, 113,  29, 
 41, 197, 137, 111, 183,  98,  14, 170,  24, 190,  27, 252,  86,  62,  75, 
198, 210, 121,  32, 154, 219, 192, 254, 120, 205,  90, 244,  31, 221, 168,
 51, 136,   7, 199,  49, 177,  18,  16,  89,  39, 128, 236,  95,  96,  81,
127, 169,  25, 181,  74,  13,  45, 229, 122, 159, 147, 201, 156, 239, 160,
224,  59,  77, 174,  42, 245, 176, 200, 235, 187,  60, 131,  83, 153,  97, 
 23,  43,   4, 126, 186, 119, 214,  38, 225, 105,  20,  99,  85,  33,  12,
125 ];

// This method circularly shifts the array left by the number of elements
// given in its parameter. It returns the resulting array and is used for 
// the ShiftRow step. Note that shift() and push() could be used for a more 
// elegant solution, but they require IE5.5+, so I chose to do it manually. 

function cyclicShiftLeft(theArray, positions) {
  var temp = theArray.slice(0, positions);
  theArray = theArray.slice(positions).concat(temp);
  return theArray;
}

// Cipher parameters ... do not change these
var Nk = keySizeInBits / 32;                   
var Nb = blockSizeInBits / 32;
var Nr = roundsArray[Nk][Nb];

// Multiplies the element "poly" of GF(2^8) by x. See the Rijndael spec.

function xtime(poly) {
  poly <<= 1;
  return ((poly & 0x100) ? (poly ^ 0x11B) : (poly));
}

// Multiplies the two elements of GF(2^8) together and returns the result.
// See the Rijndael spec, but should be straightforward: for each power of
// the indeterminant that has a 1 coefficient in x, add y times that power
// to the result. x and y should be bytes representing elements of GF(2^8)

function mult_GF256(x, y) {
  var bit, result = 0;
  
  for (bit = 1; bit < 256; bit *= 2, y = xtime(y)) {
    if (x & bit) 
      result ^= y;
  }
  return result;
}

// Performs the substitution step of the cipher.  State is the 2d array of
// state information (see spec) and direction is string indicating whether
// we are performing the forward substitution ("encrypt") or inverse 
// substitution (anything else)

function byteSub(state, direction) {
  var S;
  if (direction == "encrypt")           // Point S to the SBox we're using
    S = SBox;
  else
    S = SBoxInverse;
  for (var i = 0; i < 4; i++)           // Substitute for every byte in state
    for (var j = 0; j < Nb; j++)
       state[i][j] = S[state[i][j]];
}

// Performs the row shifting step of the cipher.

function shiftRow(state, direction) {
  for (var i=1; i<4; i++)               // Row 0 never shifts
    if (direction == "encrypt")
       state[i] = cyclicShiftLeft(state[i], shiftOffsets[Nb][i]);
    else
       state[i] = cyclicShiftLeft(state[i], Nb - shiftOffsets[Nb][i]);

}

// Performs the column mixing step of the cipher. Most of these steps can
// be combined into table lookups on 32bit values (at least for encryption)
// to greatly increase the speed. 

function mixColumn(state, direction) {
  var b = [];                            // Result of matrix multiplications
  for (var j = 0; j < Nb; j++) {         // Go through each column...
    for (var i = 0; i < 4; i++) {        // and for each row in the column...
      if (direction == "encrypt")
        b[i] = mult_GF256(state[i][j], 2) ^          // perform mixing
               mult_GF256(state[(i+1)%4][j], 3) ^ 
               state[(i+2)%4][j] ^ 
               state[(i+3)%4][j];
      else 
        b[i] = mult_GF256(state[i][j], 0xE) ^ 
               mult_GF256(state[(i+1)%4][j], 0xB) ^
               mult_GF256(state[(i+2)%4][j], 0xD) ^
               mult_GF256(state[(i+3)%4][j], 9);
    }
    for (var i = 0; i < 4; i++)          // Place result back into column
      state[i][j] = b[i];
  }
}

// Adds the current round key to the state information. Straightforward.

function addRoundKey(state, roundKey) {
  for (var j = 0; j < Nb; j++) {                 // Step through columns...
    state[0][j] ^= (roundKey[j] & 0xFF);         // and XOR
    state[1][j] ^= ((roundKey[j]>>8) & 0xFF);
    state[2][j] ^= ((roundKey[j]>>16) & 0xFF);
    state[3][j] ^= ((roundKey[j]>>24) & 0xFF);
  }
}

// This function creates the expanded key from the input (128/192/256-bit)
// key. The parameter key is an array of bytes holding the value of the key.
// The returned value is an array whose elements are the 32-bit words that 
// make up the expanded key.

function keyExpansion(key) {
  var expandedKey = new Array();
  var temp;

  // in case the key size or parameters were changed...
  Nk = keySizeInBits / 32;                   
  Nb = blockSizeInBits / 32;
  Nr = roundsArray[Nk][Nb];

  for (var j=0; j < Nk; j++)     // Fill in input key first
    expandedKey[j] = 
      (key[4*j]) | (key[4*j+1]<<8) | (key[4*j+2]<<16) | (key[4*j+3]<<24);

  // Now walk down the rest of the array filling in expanded key bytes as
  // per Rijndael's spec
  for (j = Nk, k = Nb * (Nr + 1); j < k; j++) {    // For each word of expanded key
    temp = expandedKey[j - 1];
    if (j % Nk == 0) 
      temp = ( (SBox[(temp>>8) & 0xFF]) |
               (SBox[(temp>>16) & 0xFF]<<8) |
               (SBox[(temp>>24) & 0xFF]<<16) |
               (SBox[temp & 0xFF]<<24) ) ^ Rcon[Math.floor(j / Nk) - 1];
    else if (Nk > 6 && j % Nk == 4)
      temp = (SBox[(temp>>24) & 0xFF]<<24) |
             (SBox[(temp>>16) & 0xFF]<<16) |
             (SBox[(temp>>8) & 0xFF]<<8) |
             (SBox[temp & 0xFF]);
    expandedKey[j] = expandedKey[j-Nk] ^ temp;
  }
  return expandedKey;
}

// Rijndael's round functions... 

function Round(state, roundKey) {
  byteSub(state, "encrypt");
  shiftRow(state, "encrypt");
  mixColumn(state, "encrypt");
  addRoundKey(state, roundKey);
}

function InverseRound(state, roundKey) {
  addRoundKey(state, roundKey);
  mixColumn(state, "decrypt");
  shiftRow(state, "decrypt");
  byteSub(state, "decrypt");
}

function FinalRound(state, roundKey) {
  byteSub(state, "encrypt");
  shiftRow(state, "encrypt");
  addRoundKey(state, roundKey);
}

function InverseFinalRound(state, roundKey){
  addRoundKey(state, roundKey);
  shiftRow(state, "decrypt");
  byteSub(state, "decrypt");  
}

// encrypt is the basic encryption function. It takes parameters
// block, an array of bytes representing a plaintext block, and expandedKey,
// an array of words representing the expanded key previously returned by
// keyExpansion(). The ciphertext block is returned as an array of bytes.

function encrypt(block, expandedKey) {
  var i;  
  if (!block || block.length*8 != blockSizeInBits)
     return; 
  if (!expandedKey)
     return;

  block = packBytes(block);
  addRoundKey(block, expandedKey);
  for (i=1; i<Nr; i++) 
    Round(block, expandedKey.slice(Nb*i, Nb*(i+1)));
  FinalRound(block, expandedKey.slice(Nb*Nr)); 
  return unpackBytes(block);
}

// decrypt is the basic decryption function. It takes parameters
// block, an array of bytes representing a ciphertext block, and expandedKey,
// an array of words representing the expanded key previously returned by
// keyExpansion(). The decrypted block is returned as an array of bytes.

function decrypt(block, expandedKey) {
  var i;
  if (!block || block.length*8 != blockSizeInBits)
     return;
  if (!expandedKey)
     return;

  block = packBytes(block);
  InverseFinalRound(block, expandedKey.slice(Nb*Nr)); 
  for (i = Nr - 1; i>0; i--) 
    InverseRound(block, expandedKey.slice(Nb*i, Nb*(i+1)));
  addRoundKey(block, expandedKey);
  return unpackBytes(block);
}

/* !NEEDED
// This method takes a byte array (byteArray) and converts it to a string by
// applying String.fromCharCode() to each value and concatenating the result.
// The resulting string is returned. Note that this function SKIPS zero bytes
// under the assumption that they are padding added in formatPlaintext().
// Obviously, do not invoke this method on raw data that can contain zero
// bytes. It is really only appropriate for printable ASCII/Latin-1 
// values. Roll your own function for more robust functionality :)

function byteArrayToString(byteArray) {
  var result = "";
  for(var i=0; i<byteArray.length; i++)
    if (byteArray[i] != 0) 
      result += String.fromCharCode(byteArray[i]);
  return result;
}
*/

// This function takes an array of bytes (byteArray) and converts them
// to a hexadecimal string. Array element 0 is found at the beginning of 
// the resulting string, high nibble first. Consecutive elements follow
// similarly, for example [16, 255] --> "10ff". The function returns a 
// string.

var HEX = '0123456789abcdef'.split('');

function byteArrayToHex(byteArray) {  
  if (!byteArray)
    return;
  var result = []
  for (var i = 0, k = byteArray.length; i < k; i++) {
    result.push(HEX[(byteArray[i] >> 4) & 0xf]);
    result.push(HEX[byteArray[i] & 0xf]);
  }
  return result.join('');
}

// This function converts a string containing hexadecimal digits to an 
// array of bytes. The resulting byte array is filled in the order the
// values occur in the string, for example "10FF" --> [16, 255]. This
// function returns an array. 

function hexToByteArray(hexString) {
  if (hexString.indexOf("0x") == 0 || hexString.indexOf("0X") == 0)
    hexString = hexString.substring(2);
  hexString = hexString.replace(/[^A-Fa-f0-9]/g, ''); //remove non-hex chars
  if (hexString.length % 2)             // must have even length
    return;
  var byteArray = [];
  for (var i = 0, k = hexString.length; i<k; i += 2) 
    byteArray[Math.floor(i/2)] = parseInt(hexString.slice(i, i+2), 16);
  return byteArray;
}

// This function packs an array of bytes into the four row form defined by
// Rijndael. It assumes the length of the array of bytes is divisible by
// four. Bytes are filled in according to the Rijndael spec (starting with
// column 0, row 0 to 3). This function returns a 2d array.

function packBytes(octets) {
  var state = new Array();
  if (!octets || octets.length % 4)
    return;

  state[0] = new Array();  state[1] = new Array(); 
  state[2] = new Array();  state[3] = new Array();
  for (var j=0, k = octets.length; j<k; j+= 4) {
     state[0][j/4] = octets[j];
     state[1][j/4] = octets[j+1];
     state[2][j/4] = octets[j+2];
     state[3][j/4] = octets[j+3];
  }
  return state;  
}

// This function unpacks an array of bytes from the four row format preferred
// by Rijndael into a single 1d array of bytes. It assumes the input "packed"
// is a packed array. Bytes are filled in according to the Rijndael spec. 
// This function returns a 1d array of bytes.

function unpackBytes(packed) {
  var result = new Array();
  for (var j=0, k = packed[0].length; j<k; j++) {
    result[result.length] = packed[0][j];
    result[result.length] = packed[1][j];
    result[result.length] = packed[2][j];
    result[result.length] = packed[3][j];
  }
  return result;
}

// This function takes a prospective plaintext (string or array of bytes)
// and pads it with pseudorandom bytes if its length is not a multiple of the block 
// size. If plaintext is a string, it is converted to an array of bytes
// in the process. The type checking can be made much nicer using the 
// instanceof operator, but this operator is not available until IE5.0 so I 
// chose to use the heuristic below. 

function formatPlaintext(plaintext) {
  var bpb = blockSizeInBits / 8;               // bytes per block
  var i;

  // if primitive string or String instance
  if ((!((typeof plaintext == "object") &&
        ((typeof (plaintext[0])) == "number"))) &&
      ((typeof plaintext == "string") || plaintext.indexOf)) {
    plaintext = plaintext.split("");
    // Unicode issues here (ignoring high byte)
    for (i=0,k=plaintext.length; i<k; i++)
      plaintext[i] = plaintext[i].charCodeAt(0) & 0xFF;
  }
  
  /*
  i = plaintext.length % bpb;
  if (i > 0) {
    plaintext = plaintext.concat(getRandomBytes(bpb - i));
  }
  */
  return plaintext;
}

// Returns an array containing "howMany" random bytes.

function getRandomBytes(howMany) {
    var i, bytes = new Array();
    
    for (i = 0; i < howMany; i++) {
    	bytes[i] = prng.nextInt(255);
    }
    return bytes;
}

// rijndaelEncrypt(plaintext, key, mode)
// Encrypts the plaintext using the given key and in the given mode. 
// The parameter "plaintext" can either be a string or an array of bytes. 
// The parameter "key" must be an array of key bytes. If you have a hex 
// string representing the key, invoke hexToByteArray() on it to convert it 
// to an array of bytes. The third parameter "mode" is a string indicating
// the encryption mode to use, either "ECB" or "CBC". If the parameter is
// omitted, ECB is assumed.
// 
// An array of bytes representing the cihpertext is returned. To convert 
// this array to hex, invoke byteArrayToHex() on it.

function rijndaelEncrypt(plaintext, key, mode, iv) {
  // alert(plaintext + '\n' + key + '\n' + iv);
  var expandedKey, i, aBlock;
  var bpb = blockSizeInBits / 8;          // bytes per block
  var ct;                                 // ciphertext
  if (!plaintext || !key)
    return;
  if (key.length*8 != keySizeInBits)
    return;
  iv = iv ? hexToByteArray(iv) : null;
  if (mode == "CBC") {
    ct = iv || randomB(bpb);
    // ct = iv || getRandomBytes(bpb);             // get IV
//dump("IV", byteArrayToHex(ct));
  } else {
    mode = "ECB";
    ct = new Array();
  }
  
  // convert plaintext to byte array and pad with zeros if necessary. 
  plaintext = formatPlaintext(plaintext);
  
  expandedKey = keyExpansion(key);
  
  for (var block = 0, k = plaintext.length / bpb; block < k; block++) {
    aBlock = plaintext.slice(block * bpb, (block + 1) * bpb);
    if (mode == "CBC") {
      for (var i = 0; i < bpb; i++) {
        aBlock[i] ^= ct[(block * bpb) + i];
      }
    }
    ct = ct.concat(encrypt(aBlock, expandedKey));
  }
  return ct;
}

// rijndaelDecrypt(ciphertext, key, mode)
// Decrypts the using the given key and mode. The parameter "ciphertext" 
// must be an array of bytes. The parameter "key" must be an array of key 
// bytes. If you have a hex string representing the ciphertext or key, 
// invoke hexToByteArray() on it to convert it to an array of bytes. The
// parameter "mode" is a string, either "CBC" or "ECB".
// 
// An array of bytes representing the plaintext is returned. To convert 
// this array to a hex string, invoke byteArrayToHex() on it. To convert it 
// to a string of characters, you can use byteArrayToString().

function rijndaelDecrypt(ciphertext, key, mode) {
  var expandedKey;
  var bpb = blockSizeInBits / 8;          // bytes per block
  var pt = new Array();                   // plaintext array
  var aBlock;                             // a decrypted block
  var block;                              // current block number

  if (!ciphertext || !key || typeof ciphertext == "string")
    return;
  if (key.length*8 != keySizeInBits)
    return; 
  if (!mode) {
    mode = "ECB";                         // assume ECB if mode omitted
  }

  expandedKey = keyExpansion(key);
 
  // work backwards to accomodate CBC mode 
  for (block=(ciphertext.length / bpb)-1; block>0; block--) {
    aBlock = 
     decrypt(ciphertext.slice(block*bpb,(block+1)*bpb), expandedKey);
    if (mode == "CBC") 
      for (var i=0; i<bpb; i++) 
        pt[(block-1)*bpb + i] = aBlock[i] ^ ciphertext[(block-1)*bpb + i];
    else 
      pt = aBlock.concat(pt);
  }

  // do last block if ECB (skips the IV in CBC)
  if (mode == "ECB")
    pt = decrypt(ciphertext.slice(0, bpb), expandedKey).concat(pt);

  return pt;
}

/////////////
// entropy
/////////////
    //  Entropy collection utilities

    /*	Start by declaring static storage and initialise
    	the entropy vector from the time we come through
    	here. */
	
    var entropyData = new Array();   	    // Collected entropy data
    var edlen = 0;  	    	    	    // Keyboard array data length
 
    addEntropyTime();	    	    	    // Start entropy collection with page load time
    ce();   	    	    	    	    // Roll milliseconds into initial entropy

    //	Add a byte to the entropy vector
    
    function addEntropyByte(b) {
    	entropyData[edlen++] = b;
    }
            
    /*	Capture entropy.  When the user presses a key or performs
	various other events for which we can request
	notification, add the time in 255ths of a second to the
	entropyData array.  The name of the function is short
	so it doesn't bloat the form object declarations in
	which it appears in various "onXXX" events.  */
    
    function ce() {
    	addEntropyByte(Math.floor((((new Date).getMilliseconds()) * 255) / 999));
    }
    
    //	Add a 32 bit quantity to the entropy vector
    
    function addEntropy32(w) {
    	var i;
	
	for (i = 0; i < 4; i++) {
	    addEntropyByte(w & 0xFF);
	    w >>= 8;
    	}
    }
    
    /*	Add the current time and date (milliseconds since the epoch,
    	truncated to 32 bits) to the entropy vector.  */
	
    function addEntropyTime() {
    	addEntropy32((new Date()).getTime());
    }

    /*  Start collection of entropy from mouse movements. The
	argument specifies the  number of entropy items to be
	obtained from mouse motion, after which mouse motion
	will be ignored.  Note that you can re-enable mouse
	motion collection at any time if not already underway.  */
	
    var mouseMotionCollect = 0;
    var oldMoveHandler;     	    // For saving and restoring mouse move handler in IE4
	
    function mouseMotionEntropy(maxsamp) {
    	if (mouseMotionCollect <= 0) {
	    mouseMotionCollect = maxsamp;
    	    if ((document.implementation.hasFeature("Events", "2.0")) &&
	    	document.addEventListener) {
    	    	//  Browser supports Document Object Model (DOM) 2 events
		document.addEventListener("mousemove", mouseMoveEntropy, false);
	    } else {
		if (document.attachEvent) {
	    	    //  Internet Explorer 5 and above event model
		    document.attachEvent("onmousemove", mouseMoveEntropy);
		} else {
		    //	Internet Explorer 4 event model
	    	    oldMoveHandler = document.onmousemove;
		    document.onmousemove = mouseMoveEntropy;
		}
	    }
//dump("Mouse enable", mouseMotionCollect);
	}
    }
    
    /*	Collect entropy from mouse motion events.  Note that
    	this is craftily coded to work with either DOM2 or Internet
	Explorer style events.  Note that we don't use every successive
	mouse movement event.  Instead, we XOR the three bytes collected
	from the mouse and use that to determine how many subsequent
	mouse movements we ignore before capturing the next one.  */
	
    var mouseEntropyTime = 0;	    // Delay counter for mouse entropy collection
	
    function mouseMoveEntropy(e) {
    	if (!e) {
	    e = window.event;	    // Internet Explorer event model
	}
	if (mouseMotionCollect > 0) {
	    if (mouseEntropyTime-- <= 0) {
	    	addEntropyByte(e.screenX & 0xFF);
	    	addEntropyByte(e.screenY & 0xFF);
	    	ce();
	    	mouseMotionCollect--;
	    	mouseEntropyTime = (entropyData[edlen - 3] ^ entropyData[edlen - 2] ^
		    	    	    entropyData[edlen - 1]) % 19;
//dump("Mouse Move", byteArrayToHex(entropyData.slice(-3)));
    	    }
	    if (mouseMotionCollect <= 0) {
	    	if (document.removeEventListener) {
		    document.removeEventListener("mousemove", mouseMoveEntropy, false);
		} else if (document.detachEvent) {
		    document.detachEvent("onmousemove", mouseMoveEntropy);
		} else {
		    document.onmousemove = oldMoveHandler;
		}
//dump("Spung!", 0);
	    }
	}
    }    
    
    /*	Compute a 32 byte key value from the entropy vector.
    	We compute the value by taking the MD5 sum of the even
	and odd bytes respectively of the entropy vector, then
	concatenating the two MD5 sums.  */
    
    function keyFromEntropy() {
	var i, k = new Array(32);
	
	if (edlen == 0) {
	    alert("Blooie!  Entropy vector void at call to keyFromEntropy.");
	}
//dump("Entropy bytes", edlen);

	md5_init();
	for (i = 0; i < edlen; i += 2) {
	    md5_update(entropyData[i]);
	}
	md5_finish();
    	for (i = 0; i < 16; i++) {
	    k[i] = digestBits[i];
	}

	md5_init();
	for (i = 1; i < edlen; i += 2) {
	    md5_update(entropyData[i]);
	}
	md5_finish();
    	for (i = 0; i < 16; i++) {
	    k[i + 16] = digestBits[i];
	}
	
//dump("keyFromEntropy", byteArrayToHex(k));
	return k;
    }

//////////////
// aesprng
//////////////
    //  AES based pseudorandom number generator

    /*  Constructor.  Called with an array of 32 byte (0-255) values
	containing the initial seed.  */

    function AESprng(seed) {
	this.key = new Array();
	this.key = seed;
	this.itext = hexToByteArray("9F489613248148F9C27945C6AE62EECA3E3367BB14064E4E6DC67A9F28AB3BD1");
	this.nbytes = 0;    	    // Bytes left in buffer
	
	this.next = AESprng_next;
	this.nextbits = AESprng_nextbits;
	this.nextInt = AESprng_nextInt;
	this.round = AESprng_round;
	
	/*  Encrypt the initial text with the seed key
	    three times, feeding the output of the encryption
	    back into the key for the next round.  */
	
	bsb = blockSizeInBits;
	blockSizeInBits = 256;    
	var i, ct;
    	for (i = 0; i < 3; i++) {
	    this.key = rijndaelEncrypt(this.itext, this.key, "ECB");
	}
	
	/*  Now make between one and four additional
	    key-feedback rounds, with the number determined
	    by bits from the result of the first three
	    rounds.  */
	
	var n = 1 + (this.key[3] & 2) + (this.key[9] & 1);    
    	for (i = 0; i < n; i++) {
	    this.key = rijndaelEncrypt(this.itext, this.key, "ECB");
	}
    	blockSizeInBits = bsb;
    }
    
    function AESprng_round() {
	bsb = blockSizeInBits;
	blockSizeInBits = 256;    
    	this.key = rijndaelEncrypt(this.itext, this.key, "ECB");
	this.nbytes = 32;
    	blockSizeInBits = bsb;
    }
    
    //	Return next byte from the generator

    function AESprng_next() {
    	if (this.nbytes <= 0) {
	    this.round();
	}
	return(this.key[--this.nbytes]);
    }
    
    //	Return n bit integer value (up to maximum integer size)
    
    function AESprng_nextbits(n) {
    	var i, w = 0, nbytes = Math.floor((n + 7) / 8);

	for (i = 0; i < nbytes; i++) {
	    w = (w << 8) | this.next();
	}
	return w & ((1 << n) - 1);
    }

    //  Return integer between 0 and n inclusive
    
    function AESprng_nextInt(n) {
    	var p = 1, nb = 0;
	
	//  Determine smallest p,  2^p > n
	//  nb = log_2 p
	
	while (n >= p) {
	    p <<= 1;
	    nb++;
	}
	p--;
	
	/*  Generate values from 0 through n by first generating
	    values v from 0 to (2^p)-1, then discarding any results v > n.
	    For the rationale behind this (and why taking
	    values mod (n + 1) is biased toward smaller values, see
	    Ferguson and Schneier, "Practical Cryptography",
	    ISBN 0-471-22357-3, section 10.8).  */

	while (true) {
    	    var v = this.nextbits(nb) & p;
	    
	    if (v <= n) {
	    	return v;
	    }
	}
    }

///////////////
// md5
///////////////
/*
 *  md5.jvs 1.0b 27/06/96
 *
 * Javascript implementation of the RSA Data Security, Inc. MD5
 * Message-Digest Algorithm.
 *
 * Copyright (c) 1996 Henri Torgemane. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for any purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. 
 *
 * Of course, this soft is provided "as is" without express or implied
 * warranty of any kind.

    This version contains some trivial reformatting modifications
    by John Walker.

 */

function array(n) {
    for (i = 0; i < n; i++) {
        this[i] = 0;
    }
    this.length = n;
}

/* Some basic logical functions had to be rewritten because of a bug in
 * Javascript.. Just try to compute 0xffffffff >> 4 with it..
 * Of course, these functions are slower than the original would be, but
 * at least, they work!
 */

function integer(n) {
    return n % (0xffffffff + 1);
}

function shr(a, b) {
    a = integer(a);
    b = integer(b);
    if (a - 0x80000000 >= 0) {
        a = a % 0x80000000;
        a >>= b;
        a += 0x40000000 >> (b - 1);
    } else {
        a >>= b;
    }
    return a;
}

function shl1(a) {
    a = a % 0x80000000;
    if (a & 0x40000000 == 0x40000000) {
        a -= 0x40000000;  
        a *= 2;
        a += 0x80000000;
    } else {
        a *= 2;
    }
    return a;
}

function shl(a, b) {
    a = integer(a);
    b = integer(b);
    for (var i = 0; i < b; i++) {
        a = shl1(a);
    }
    return a;
}

function and(a, b) {
    a = integer(a);
    b = integer(b);
    var t1 = a - 0x80000000;
    var t2 = b - 0x80000000;
    if (t1 >= 0) {
        if (t2 >= 0) {
            return ((t1 & t2) + 0x80000000);
        } else {
            return (t1 & b);
        }
    } else {
        if (t2 >= 0) {
            return (a & t2);
        } else {
            return (a & b);  
        }
    }
}

function or(a, b) {
    a = integer(a);
    b = integer(b);
    var t1 = a - 0x80000000;
    var t2 = b - 0x80000000;
    if (t1 >= 0) {
        if (t2 >= 0) {
            return ((t1 | t2) + 0x80000000);
        } else {
            return ((t1 | b) + 0x80000000);
        }
    } else {
        if (t2 >= 0) {
            return ((a | t2) + 0x80000000);
        } else {
            return (a | b);  
        }
    }
}

function xor(a, b) {
    a = integer(a);
    b = integer(b);
    var t1 = a - 0x80000000;
    var t2 = b - 0x80000000;
    if (t1 >= 0) {
        if (t2 >= 0) {
            return (t1 ^ t2);
        } else {
            return ((t1 ^ b) + 0x80000000);
        }
    } else {
        if (t2 >= 0) {
            return ((a ^ t2) + 0x80000000);
        } else {
            return (a ^ b);  
        }
    }
}

function not(a) {
    a = integer(a);
    return 0xffffffff - a;
}

/* Here begin the real algorithm */

var state = new array(4); 
var count = new array(2);
    count[0] = 0;
    count[1] = 0;                     
var buffer = new array(64); 
var transformBuffer = new array(16); 
var digestBits = new array(16);

var S11 = 7;
var S12 = 12;
var S13 = 17;
var S14 = 22;
var S21 = 5;
var S22 = 9;
var S23 = 14;
var S24 = 20;
var S31 = 4;
var S32 = 11;
var S33 = 16;
var S34 = 23;
var S41 = 6;
var S42 = 10;
var S43 = 15;
var S44 = 21;

function F(x, y, z) {
    return or(and(x, y), and(not(x), z));
}

function G(x, y, z) {
    return or(and(x, z), and(y, not(z)));
}

function H(x, y, z) {
    return xor(xor(x, y), z);
}

function I(x, y, z) {
    return xor(y ,or(x , not(z)));
}

function rotateLeft(a, n) {
    return or(shl(a, n), (shr(a, (32 - n))));
}

function FF(a, b, c, d, x, s, ac) {
    a = a + F(b, c, d) + x + ac;
    a = rotateLeft(a, s);
    a = a + b;
    return a;
}

function GG(a, b, c, d, x, s, ac) {
    a = a + G(b, c, d) + x + ac;
    a = rotateLeft(a, s);
    a = a + b;
    return a;
}

function HH(a, b, c, d, x, s, ac) {
    a = a + H(b, c, d) + x + ac;
    a = rotateLeft(a, s);
    a = a + b;
    return a;
}

function II(a, b, c, d, x, s, ac) {
    a = a + I(b, c, d) + x + ac;
    a = rotateLeft(a, s);
    a = a + b;
    return a;
}

function transform(buf, offset) { 
    var a = 0, b = 0, c = 0, d = 0; 
    var x = transformBuffer;
    
    a = state[0];
    b = state[1];
    c = state[2];
    d = state[3];
    
    for (i = 0; i < 16; i++) {
        x[i] = and(buf[i * 4 + offset], 0xFF);
        for (j = 1; j < 4; j++) {
            x[i] += shl(and(buf[i * 4 + j + offset] ,0xFF), j * 8);
        }
    }

    /* Round 1 */
    a = FF( a, b, c, d, x[ 0], S11, 0xd76aa478); /* 1 */
    d = FF( d, a, b, c, x[ 1], S12, 0xe8c7b756); /* 2 */
    c = FF( c, d, a, b, x[ 2], S13, 0x242070db); /* 3 */
    b = FF( b, c, d, a, x[ 3], S14, 0xc1bdceee); /* 4 */
    a = FF( a, b, c, d, x[ 4], S11, 0xf57c0faf); /* 5 */
    d = FF( d, a, b, c, x[ 5], S12, 0x4787c62a); /* 6 */
    c = FF( c, d, a, b, x[ 6], S13, 0xa8304613); /* 7 */
    b = FF( b, c, d, a, x[ 7], S14, 0xfd469501); /* 8 */
    a = FF( a, b, c, d, x[ 8], S11, 0x698098d8); /* 9 */
    d = FF( d, a, b, c, x[ 9], S12, 0x8b44f7af); /* 10 */
    c = FF( c, d, a, b, x[10], S13, 0xffff5bb1); /* 11 */
    b = FF( b, c, d, a, x[11], S14, 0x895cd7be); /* 12 */
    a = FF( a, b, c, d, x[12], S11, 0x6b901122); /* 13 */
    d = FF( d, a, b, c, x[13], S12, 0xfd987193); /* 14 */
    c = FF( c, d, a, b, x[14], S13, 0xa679438e); /* 15 */
    b = FF( b, c, d, a, x[15], S14, 0x49b40821); /* 16 */

    /* Round 2 */
    a = GG( a, b, c, d, x[ 1], S21, 0xf61e2562); /* 17 */
    d = GG( d, a, b, c, x[ 6], S22, 0xc040b340); /* 18 */
    c = GG( c, d, a, b, x[11], S23, 0x265e5a51); /* 19 */
    b = GG( b, c, d, a, x[ 0], S24, 0xe9b6c7aa); /* 20 */
    a = GG( a, b, c, d, x[ 5], S21, 0xd62f105d); /* 21 */
    d = GG( d, a, b, c, x[10], S22,  0x2441453); /* 22 */
    c = GG( c, d, a, b, x[15], S23, 0xd8a1e681); /* 23 */
    b = GG( b, c, d, a, x[ 4], S24, 0xe7d3fbc8); /* 24 */
    a = GG( a, b, c, d, x[ 9], S21, 0x21e1cde6); /* 25 */
    d = GG( d, a, b, c, x[14], S22, 0xc33707d6); /* 26 */
    c = GG( c, d, a, b, x[ 3], S23, 0xf4d50d87); /* 27 */
    b = GG( b, c, d, a, x[ 8], S24, 0x455a14ed); /* 28 */
    a = GG( a, b, c, d, x[13], S21, 0xa9e3e905); /* 29 */
    d = GG( d, a, b, c, x[ 2], S22, 0xfcefa3f8); /* 30 */
    c = GG( c, d, a, b, x[ 7], S23, 0x676f02d9); /* 31 */
    b = GG( b, c, d, a, x[12], S24, 0x8d2a4c8a); /* 32 */

    /* Round 3 */
    a = HH( a, b, c, d, x[ 5], S31, 0xfffa3942); /* 33 */
    d = HH( d, a, b, c, x[ 8], S32, 0x8771f681); /* 34 */
    c = HH( c, d, a, b, x[11], S33, 0x6d9d6122); /* 35 */
    b = HH( b, c, d, a, x[14], S34, 0xfde5380c); /* 36 */
    a = HH( a, b, c, d, x[ 1], S31, 0xa4beea44); /* 37 */
    d = HH( d, a, b, c, x[ 4], S32, 0x4bdecfa9); /* 38 */
    c = HH( c, d, a, b, x[ 7], S33, 0xf6bb4b60); /* 39 */
    b = HH( b, c, d, a, x[10], S34, 0xbebfbc70); /* 40 */
    a = HH( a, b, c, d, x[13], S31, 0x289b7ec6); /* 41 */
    d = HH( d, a, b, c, x[ 0], S32, 0xeaa127fa); /* 42 */
    c = HH( c, d, a, b, x[ 3], S33, 0xd4ef3085); /* 43 */
    b = HH( b, c, d, a, x[ 6], S34,  0x4881d05); /* 44 */
    a = HH( a, b, c, d, x[ 9], S31, 0xd9d4d039); /* 45 */
    d = HH( d, a, b, c, x[12], S32, 0xe6db99e5); /* 46 */
    c = HH( c, d, a, b, x[15], S33, 0x1fa27cf8); /* 47 */
    b = HH( b, c, d, a, x[ 2], S34, 0xc4ac5665); /* 48 */

    /* Round 4 */
    a = II( a, b, c, d, x[ 0], S41, 0xf4292244); /* 49 */
    d = II( d, a, b, c, x[ 7], S42, 0x432aff97); /* 50 */
    c = II( c, d, a, b, x[14], S43, 0xab9423a7); /* 51 */
    b = II( b, c, d, a, x[ 5], S44, 0xfc93a039); /* 52 */
    a = II( a, b, c, d, x[12], S41, 0x655b59c3); /* 53 */
    d = II( d, a, b, c, x[ 3], S42, 0x8f0ccc92); /* 54 */
    c = II( c, d, a, b, x[10], S43, 0xffeff47d); /* 55 */
    b = II( b, c, d, a, x[ 1], S44, 0x85845dd1); /* 56 */
    a = II( a, b, c, d, x[ 8], S41, 0x6fa87e4f); /* 57 */
    d = II( d, a, b, c, x[15], S42, 0xfe2ce6e0); /* 58 */
    c = II( c, d, a, b, x[ 6], S43, 0xa3014314); /* 59 */
    b = II( b, c, d, a, x[13], S44, 0x4e0811a1); /* 60 */
    a = II( a, b, c, d, x[ 4], S41, 0xf7537e82); /* 61 */
    d = II( d, a, b, c, x[11], S42, 0xbd3af235); /* 62 */
    c = II( c, d, a, b, x[ 2], S43, 0x2ad7d2bb); /* 63 */
    b = II( b, c, d, a, x[ 9], S44, 0xeb86d391); /* 64 */

    state[0] += a;
    state[1] += b;
    state[2] += c;
    state[3] += d;

}

function md5_init() {
    count[0] = count[1] = 0;
    state[0] = 0x67452301;
    state[1] = 0xefcdab89;
    state[2] = 0x98badcfe;
    state[3] = 0x10325476;
    for (i = 0; i < digestBits.length; i++) {
        digestBits[i] = 0;
    }
}

function md5_update(b) { 
    var index, i;
    
    index = and(shr(count[0],3) , 0x3F);
    if (count[0] < 0xFFFFFFFF - 7) {
      count[0] += 8;
    } else {
      count[1]++;
      count[0] -= 0xFFFFFFFF + 1;
      count[0] += 8;
    }
    buffer[index] = and(b, 0xff);
    if (index  >= 63) {
        transform(buffer, 0);
    }
}

function md5_finish() {
    var bits = new array(8);
    var padding; 
    var i = 0, index = 0, padLen = 0;

    for (i = 0; i < 4; i++) {
        bits[i] = and(shr(count[0], (i * 8)), 0xFF);
    }
    for (i = 0; i < 4; i++) {
        bits[i + 4] = and(shr(count[1], (i * 8)), 0xFF);
    }
    index = and(shr(count[0], 3), 0x3F);
    padLen = (index < 56) ? (56 - index) : (120 - index);
    padding = new array(64); 
    padding[0] = 0x80;
    for (i = 0; i < padLen; i++) {
      md5_update(padding[i]);
    }
    for (i = 0; i < 8; i++) {
      md5_update(bits[i]);
    }

    for (i = 0; i < 4; i++) {
        for (j = 0; j < 4; j++) {
            digestBits[i * 4 + j] = and(shr(state[i], (j * 8)) , 0xFF);
        }
    } 
}

/* End of the MD5 algorithm */

/////////////
// jscrypt
/////////////
//	    	JavaScrypt  --  Main page support functions

//	    For details, see http://www.fourmilab.ch/javascrypt/

var loadTime = (new Date()).getTime();  // Save time page was loaded
var key;	    	    	    	    // Key (byte array)
var prng;	    	    	    	    // Pseudorandom number generator
    
//	setKey  --  Set key from string or hexadecimal specification

function setKey(keystr) {
    var s = keystr;
    var hexDigits = "0123456789abcdefABCDEF";
    var hs = "", i, bogus = false;

    for (i = 0; i < s.length; i++) {
        var c = s.charAt(i);
        if (hexDigits.indexOf(c) >= 0) {
                hs += c;
        } else {
            bogus = true;
        }
    }
    if (bogus) {
        alert("Error: Non-Hexadecimal character(s) found in Hexadecimal key.");
    }
    if (hs.length > (keySizeInBits / 4)) {
        alert("Warning: hexadecimal key exceeds " +
        (keySizeInBits / 4) + " digit maximum; truncated.");
        hs = hs.slice(0, 64);
    } else {
        //  If key is fewer than 64 hex digits, fill it with zeroes
        while (hs.length < (keySizeInBits / 4)) {
            hs += "0";
        }
    }
    key =  hexToByteArray(hs);
}

/*	Generate a key from the pseudorandom number generator
    and stuff it in the key field.  The kind of key generated
(text or hexadecimal) is determined by which box is checked
below the key field.
Returns a 256 bit / 32 Byte key.
*/

function Generate_key() {
    var i, j, k = [];
    addEntropyTime();
    var seed = keyFromEntropy();
    
    var prng = new AESprng(seed);
    
    for (i = 0; i < 64; i++) {
        k.push(HEX[prng.nextInt(15)]);
    }
    return k.join('');
}

var Utf8 = {};  // Utf8 namespace

/**
 * Encode multi-byte Unicode string into utf-8 multiple single-byte characters 
 * (BMP / basic multilingual plane only)
 *
 * Chars in range U+0080 - U+07FF are encoded in 2 chars, U+0800 - U+FFFF in 3 chars
 *
 * @param {String} strUni Unicode string to be encoded as UTF-8
 * @returns {String} encoded string
 */
Utf8.encode = function(strUni) {
  // use regular expressions & String.replace callback function for better efficiency 
  // than procedural approaches
  var strUtf = strUni.replace(
      /[\u0080-\u07ff]/g,  // U+0080 - U+07FF => 2 bytes 110yyyyy, 10zzzzzz
      function(c) { 
        var cc = c.charCodeAt(0);
        return String.fromCharCode(0xc0 | cc>>6, 0x80 | cc&0x3f); }
    );
  strUtf = strUtf.replace(
      /[\u0800-\uffff]/g,  // U+0800 - U+FFFF => 3 bytes 1110xxxx, 10yyyyyy, 10zzzzzz
      function(c) { 
        var cc = c.charCodeAt(0); 
        return String.fromCharCode(0xe0 | cc>>12, 0x80 | cc>>6&0x3F, 0x80 | cc&0x3f); }
    );
  return strUtf;
}

//takes plaintext and a hex key, returns a hex string ciphertext
function aesEncrypt(plaintext, keystr, iv) {
    if (keystr.length == 0) {
        return "";
    }
    if (plaintext.length == 0) {
        return "";
    }

    plaintext = Utf8.encode(plaintext);

    setKey(keystr);
    addEntropyTime();
    // prng = new AESprng(keyFromEntropy());
    var v = "";
    var temp = 16 - (plaintext.length % 16);
	  for(var i=1; i<temp; i++) {
      plaintext += String.fromCharCode(rand(256));
    }
    plaintext += String.fromCharCode(temp);
    var ct = rijndaelEncrypt(plaintext, key, "CBC", iv);
    return ct;
}

//takes a hex string ciphertext and hex key and returns string plaintext
function Decrypt(ciphertext, keystr) {
    if (keystr.length == 0) {
        alert("Please specify a key with which to decrypt the message.");
        return "";
    }
    if (ciphertext.length == 0) {
        alert("Nothing to decrypt!");
        return "";
    }
    setKey(keystr);
    
    var array = hexToByteArray(ciphertext);
    var result = rijndaelDecrypt(array, key, "CBC");
    
    var char_str = "";
    for(var i=0; i<result.length; i++) {
        char_str += String.fromCharCode(result[i])+" ";
    }
    
    var plaintext = "";
    
    for (var i = 0; i < result.length; i++) {
        plaintext += String.fromCharCode(result[i]);
    }
    //remove all null chars from end of output
    plaintext = plaintext.replace(/\0*$/g, "");
    
    if(!plaintext) return "";
    return result;
}

/**
 *  CryptoMX Tools
 *  Copyright (C) 2004 - 2006 Derek Buitenhuis
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Modified February 2009 by TimStamp.co.uk - Modifications include:
 * + moved keyStr inside functions to prevent external variable clashes
 * + added regular expression replace terms to remove illegal characters
 * + added code to encode64(str) function to add the correct number of equals
 *   signs, which are supposed to be used to pad the output string to a
 *   length divisible by 4. This prevents the decode64(str) function from 
 *   adding unwanted null values at the end of the output.
 * + Also added line-breaks on the encode function at 64 chars.
 **/
 
function encode64(input) {

    var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    //remove null characters from end of output
    input = input.replace(/\0*$/g, '');
    
    var output = "";
    var chr1, chr2, chr3 = "";
    var enc1, enc2, enc3, enc4 = "";
    var i = 0;

    do {
        chr1 = input.charCodeAt(i++);
        chr2 = input.charCodeAt(i++);
        chr3 = input.charCodeAt(i++);

        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;

        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }

        output = output +
            keyStr.charAt(enc1) +
            keyStr.charAt(enc2) +
            keyStr.charAt(enc3) +
            keyStr.charAt(enc4);
        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";
    } while (i < input.length);

    var strout = ""
    output = output.split('');
    for(var c=0; c<output.length; c++) {
        if(c % 64 == 0 && c > 0) strout += '\n';
        strout += output[c];
    }
    output = output.join();
    
    var nulls = strout % 4;
    for(var i=0; i<nulls; i++)
        strout += '=';
    
    return strout;
}

function decode64(input) {

    var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    var output = "";
    var chr1, chr2, chr3 = "";
    var enc1, enc2, enc3, enc4 = "";
    var i = 0;

    // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
    input = input.replace(/[^A-Za-z0-9\+\/\=\/n]/g, "");

    do {
        enc1 = keyStr.indexOf(input.charAt(i++));
        enc2 = keyStr.indexOf(input.charAt(i++));
        enc3 = keyStr.indexOf(input.charAt(i++));
        enc4 = keyStr.indexOf(input.charAt(i++));

        chr1 = (enc1 << 2) | (enc2 >> 4);
        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
        chr3 = ((enc3 & 3) << 6) | enc4;

        output = output + String.fromCharCode(chr1);

        if (enc3 != 64) {
            output = output + String.fromCharCode(chr2);
        }
        if (enc4 != 64) {
            output = output + String.fromCharCode(chr3);
        }

        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";

    } while (i < input.length);
    
    //remove null characters from end of output
    output = output.replace(/\0*$/g, "");

    return output;
}

Security.encryptHex = function() {
  return byteArrayToHex(aesEncrypt.apply(this, arguments).slice(16));
}
Security.decryptHex = function() {
  return byteArrayToHex(Decrypt.apply(this, arguments));
};
Security.encrypt64 = function() {
  return encode64(aesEncrypt.apply(this, arguments));
}
Security.decrypt64 = function() {
  return decode64(Decrypt.apply(this, arguments));
};
Security.generateKey = Generate_key;

Security.generateKeyQuick = function() {
  return byteArrayToHex(randomB(32));
};

Security.generateIvQuick = function() {
  return byteArrayToHex(randomB(16));
};

Security.encode64 = encode64;
Security.decode64 = decode64;
})();


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
/*  SHA-1 implementation in JavaScript | (c) Chris Veness 2002-2010 | www.movable-type.co.uk      */
/*   - see http://csrc.nist.gov/groups/ST/toolkit/secure_hashing.html                             */
/*         http://csrc.nist.gov/groups/ST/toolkit/examples.html                                   */
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */

var Sha1 = {};  // Sha1 namespace

/**
 * Generates SHA-1 hash of string
 *
 * @param {String} msg                String to be hashed
 * @param {Boolean} [utf8encode=true] Encode msg as UTF-8 before generating hash
 * @returns {String}                  Hash of msg as hex character string
 */
Sha1.hash = function(msg, utf8encode) {
  utf8encode =  (typeof utf8encode == 'undefined') ? true : utf8encode;
  
  // convert string to UTF-8, as SHA only deals with byte-streams
  if (utf8encode) msg = Utf8.encode(msg);
  
  // constants [��4.2.1]
  var K = [0x5a827999, 0x6ed9eba1, 0x8f1bbcdc, 0xca62c1d6];
  
  // PREPROCESSING 
  
  msg += String.fromCharCode(0x80);  // add trailing '1' bit (+ 0's padding) to string [��5.1.1]
  
  // convert string msg into 512-bit/16-integer blocks arrays of ints [��5.2.1]
  var l = msg.length/4 + 2;  // length (in 32-bit integers) of msg + ��1�� + appended length
  var N = Math.ceil(l/16);   // number of 16-integer-blocks required to hold 'l' ints
  var M = new Array(N);
  
  for (var i=0; i<N; i++) {
    M[i] = new Array(16);
    for (var j=0; j<16; j++) {  // encode 4 chars per integer, big-endian encoding
      M[i][j] = (msg.charCodeAt(i*64+j*4)<<24) | (msg.charCodeAt(i*64+j*4+1)<<16) | 
        (msg.charCodeAt(i*64+j*4+2)<<8) | (msg.charCodeAt(i*64+j*4+3));
    } // note running off the end of msg is ok 'cos bitwise ops on NaN return 0
  }
  // add length (in bits) into final pair of 32-bit integers (big-endian) [��5.1.1]
  // note: most significant word would be (len-1)*8 >>> 32, but since JS converts
  // bitwise-op args to 32 bits, we need to simulate this by arithmetic operators
  M[N-1][14] = ((msg.length-1)*8) / Math.pow(2, 32); M[N-1][14] = Math.floor(M[N-1][14])
  M[N-1][15] = ((msg.length-1)*8) & 0xffffffff;
  
  // set initial hash value [��5.3.1]
  var H0 = 0x67452301;
  var H1 = 0xefcdab89;
  var H2 = 0x98badcfe;
  var H3 = 0x10325476;
  var H4 = 0xc3d2e1f0;
  
  // HASH COMPUTATION [��6.1.2]
  
  var W = new Array(80); var a, b, c, d, e;
  for (var i=0; i<N; i++) {
  
    // 1 - prepare message schedule 'W'
    for (var t=0;  t<16; t++) W[t] = M[i][t];
    for (var t=16; t<80; t++) W[t] = Sha1.ROTL(W[t-3] ^ W[t-8] ^ W[t-14] ^ W[t-16], 1);
    
    // 2 - initialise five working variables a, b, c, d, e with previous hash value
    a = H0; b = H1; c = H2; d = H3; e = H4;
    
    // 3 - main loop
    for (var t=0; t<80; t++) {
      var s = Math.floor(t/20); // seq for blocks of 'f' functions and 'K' constants
      var T = (Sha1.ROTL(a,5) + Sha1.f(s,b,c,d) + e + K[s] + W[t]) & 0xffffffff;
      e = d;
      d = c;
      c = Sha1.ROTL(b, 30);
      b = a;
      a = T;
    }
    
    // 4 - compute the new intermediate hash value
    H0 = (H0+a) & 0xffffffff;  // note 'addition modulo 2^32'
    H1 = (H1+b) & 0xffffffff; 
    H2 = (H2+c) & 0xffffffff; 
    H3 = (H3+d) & 0xffffffff; 
    H4 = (H4+e) & 0xffffffff;
  }

  return Sha1.toHexStr(H0) + Sha1.toHexStr(H1) + 
    Sha1.toHexStr(H2) + Sha1.toHexStr(H3) + Sha1.toHexStr(H4);
}

//
// function 'f' [��4.1.1]
//
Sha1.f = function(s, x, y, z)  {
  switch (s) {
  case 0: return (x & y) ^ (~x & z);           // Ch()
  case 1: return x ^ y ^ z;                    // Parity()
  case 2: return (x & y) ^ (x & z) ^ (y & z);  // Maj()
  case 3: return x ^ y ^ z;                    // Parity()
  }
}

//
// rotate left (circular left shift) value x by n positions [��3.2.5]
//
Sha1.ROTL = function(x, n) {
  return (x<<n) | (x>>>(32-n));
}

//
// hexadecimal representation of a number 
//   (note toString(16) is implementation-dependant, and  
//   in IE returns signed numbers when used on full words)
//
Sha1.toHexStr = function(n) {
  var s="", v;
  for (var i=7; i>=0; i--) { v = (n>>>(i*4)) & 0xf; s += v.toString(16); }
  return s;
}


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
/*  Utf8 class: encode / decode between multi-byte Unicode characters and UTF-8 multiple          */
/*              single-byte character encoding (c) Chris Veness 2002-2010                         */
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */

var Utf8 = {};  // Utf8 namespace

/**
 * Encode multi-byte Unicode string into utf-8 multiple single-byte characters 
 * (BMP / basic multilingual plane only)
 *
 * Chars in range U+0080 - U+07FF are encoded in 2 chars, U+0800 - U+FFFF in 3 chars
 *
 * @param {String} strUni Unicode string to be encoded as UTF-8
 * @returns {String} encoded string
 */
Utf8.encode = function(strUni) {
  // use regular expressions & String.replace callback function for better efficiency 
  // than procedural approaches
  var strUtf = strUni.replace(
      /[\u0080-\u07ff]/g,  // U+0080 - U+07FF => 2 bytes 110yyyyy, 10zzzzzz
      function(c) { 
        var cc = c.charCodeAt(0);
        return String.fromCharCode(0xc0 | cc>>6, 0x80 | cc&0x3f); }
    );
  strUtf = strUtf.replace(
      /[\u0800-\uffff]/g,  // U+0800 - U+FFFF => 3 bytes 1110xxxx, 10yyyyyy, 10zzzzzz
      function(c) { 
        var cc = c.charCodeAt(0); 
        return String.fromCharCode(0xe0 | cc>>12, 0x80 | cc>>6&0x3F, 0x80 | cc&0x3f); }
    );
  return strUtf;
}

/**
 * Decode utf-8 encoded string back into multi-byte Unicode characters
 *
 * @param {String} strUtf UTF-8 string to be decoded back to Unicode
 * @returns {String} decoded string
 */
Utf8.decode = function(strUtf) {
  alert(strUtf);
  // note: decode 3-byte chars first as decoded 2-byte strings could appear to be 3-byte char!
  var strUni = strUtf.replace(
      /[\u00e0-\u00ef][\u0080-\u00bf][\u0080-\u00bf]/g,  // 3-byte chars
      function(c) {  // (note parentheses for precence)
        var cc = ((c.charCodeAt(0)&0x0f)<<12) | ((c.charCodeAt(1)&0x3f)<<6) | ( c.charCodeAt(2)&0x3f); 
        return String.fromCharCode(cc); }
    );
  strUni = strUni.replace(
      /[\u00c0-\u00df][\u0080-\u00bf]/g,                 // 2-byte chars
      function(c) {  // (note parentheses for precence)
        var cc = (c.charCodeAt(0)&0x1f)<<6 | c.charCodeAt(1)&0x3f;
        return String.fromCharCode(cc); }
    );
  return strUni;
}

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */

var Encrypt = {};

(function() {

  Encrypt.__DEFAULT_OPTIONS__ = {
    handshake : 'http://www.pinju.com/e/handshake.htm',
    timeout : 10000,
    success : function( key, iv, result ) { return; },
    error : function( key, iv, result ) { return; }
  };

  Encrypt.encrypt = function( options ) {
    var op = jQuery.extend( {}, Encrypt.__DEFAULT_OPTIONS__, options );
    generateKey( function( key, iv ) {
      encryptKey( key, iv, function( encKey ) {
        handshake( encKey, op.handshake, op.timeout, function( result ) {
          if ( challenge( key, iv, result ) ) {
            op.success.call( this, key, iv, result );
          } else if ( op.error && jQuery.isFunction( op.error ) ) {
            op.error.call( this, key, iv, result );
          }
        });
      });
    });
  };

  var challenge = function( key, iv, obj ) {
    if ( !obj ) {
      return false;
    }
    return Sha1.hash( key + iv + obj.tid ) === obj.keh;
  };

  var generateKey = function( callback ) {
    var key = Security.generateKeyQuick();
    var iv = Security.generateIvQuick();
    callback.call( this, key, iv );
  };

  var encryptKey = function( key, iv, callback ) {
    var encKey = linebrk(hex2b64(FormSalt.encrypt(key + iv)));
    callback.call( this, encKey );
  };

  var handshake = function( encKey, url, timeout, callback ) {
    jQuery.ajax({
			url: url,
			dataType: 'json',
			type: 'POST',
      timeout: timeout,
			data: { handshake: encKey },
			success: function( result ) {
        callback.call( this, result );
			},
      error : function(v, x) {
        callback.call( this );
      }
		});
  };
})();