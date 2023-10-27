#include <iostream>
#include <string>
#include <vector>
#include <cmath>
#include <algorithm>

using namespace std;

vector<string> getBlockBits(string inputString, int lengthBlock) {
    vector<string> blocksBits;
    string linesBits = "";
    for (int i = 0; i < inputString.length(); i++) {
        unsigned char charLetter = (char)inputString[i];
        string lineBits = "";
        for (int j = 0; j < 8; j++, charLetter >>= 1)
        {
            if (linesBits.length() == lengthBlock) {
                blocksBits.push_back(linesBits);
                linesBits = "";
            }
            lineBits = char('0' + charLetter % 2) + lineBits;

        }
        linesBits += lineBits;
    }

    while (linesBits.length() != lengthBlock) {
        linesBits = '0' + linesBits;
    }
    blocksBits.push_back(linesBits);
    return blocksBits;
}

string getEncodeText(vector<string> inputBlocksBits) {
    vector<string> blocksBits;
    string decodeString = "";
    for (int i = 0; i < inputBlocksBits.size(); i++) {
        for (int j = 7; j < inputBlocksBits[i].size(); j += 8) {

            decodeString += (char) 
                (inputBlocksBits[i][j - 7] % 2) * 128 +
                (inputBlocksBits[i][j - 6] % 2) * 64 +
                (inputBlocksBits[i][j - 5] % 2) * 32 +
                (inputBlocksBits[i][j - 4] % 2) * 16 +
                (inputBlocksBits[i][j - 3] % 2) * 8 +
                (inputBlocksBits[i][j - 2] % 2) * 4 +
                (inputBlocksBits[i][j - 1] % 2) * 2 +
                (inputBlocksBits[i][j] % 2) * 1;
        }
    }

    return decodeString;
}

string encode(string inputString, string key, vector<int> replacementVector) {
    //-1 
    vector<string> inputBlocksBits = getBlockBits(inputString, 64);
    vector<string> keyBlocksBits = getBlockBits(key, 32);

    for (int k = 0; k < inputBlocksBits.size(); k++)
    {
        string L = inputBlocksBits[k].substr(0, inputBlocksBits[k].size() / 2);
        string R = inputBlocksBits[k].substr(inputBlocksBits[k].size() / 2, inputBlocksBits[k].size());
        for (int i = 1; i < 32; i++) {
            //3
            int j;
            if (i < 25) {
                j = (i - 1) % 8;
            }
            else {
                j = (32 - i) % 8;
            }

            //4
            string LConverted = L;
            for (int n = 0; n < L.size(); n++)
            {
               LConverted[n] = ((L[n] % 2) ^ (keyBlocksBits[j][n] % 2));
            }

            for (int n = 3; n < LConverted.size(); n += 4)
            {
                int replacementBlockKey =
                    (LConverted[n - 3] % 2) * 8 +
                    (LConverted[n - 2] % 2) * 4 +
                    (LConverted[n - 1] % 2) * 2 +
                    (LConverted[n] % 2) * 1;
                int replacementBlockValue = replacementVector[replacementBlockKey];
                LConverted[n - 3] = replacementBlockValue / 8;
                replacementBlockValue = replacementBlockValue % 8;
                LConverted[n - 2] = replacementBlockValue / 4;
                replacementBlockValue = replacementBlockValue % 4;
                LConverted[n - 1] = replacementBlockValue / 2;
                replacementBlockValue = replacementBlockValue % 2;
                LConverted[n] = replacementBlockValue / 1;
            }

            string shiftBits = "";
            for (int n = 0; n < 11; n++)
            {
                shiftBits += LConverted[n] % 2;
            }
            for (int n = 0; n < LConverted.size() - 11; n++)
            {
                LConverted[n] = LConverted[n + 11] % 2;
            }
            for (int n = LConverted.size() - 11; n < LConverted.size(); n++)
            {
                LConverted[n] = shiftBits[n - LConverted.size() + 11] % 2;
            }

            for (int n = 0; n < LConverted.size(); n++)
            {
                R[n] = ((R[n] % 2) ^ (LConverted[n] % 2));
            }

            //5
            string V = R;
            R = L;
            L = V;
        }

        inputBlocksBits[k] = L + R;
    }

    return getEncodeText(inputBlocksBits);
}

string decode(string encodeString, string key, vector<int> replacementVector) {
    //-1 
    vector<string> inputBlocksBits = getBlockBits(encodeString, 64);
    vector<string> keyBlocksBits = getBlockBits(key, 32);

    for (int k = 0; k < inputBlocksBits.size(); k++)
    {
        string L = inputBlocksBits[k].substr(0, inputBlocksBits[k].size() / 2);
        string R = inputBlocksBits[k].substr(inputBlocksBits[k].size() / 2, inputBlocksBits[k].size());
        for (int i = 31; i > 0; i--) {
            //2
            string V = R;
            R = L;
            L = V;

            //3
            int j;
            if (i < 25) {
                j = (i - 1) % 8;
            } else {
                j = (32 - i) % 8;
            }

            //4
            string LConverted = L;
            for (int n = 0; n < L.size(); n++)
            {
                LConverted[n] = ((L[n] % 2) ^ (keyBlocksBits[j][n] % 2));
            }

            for (int n = 3; n < LConverted.size(); n += 4)
            {
                int replacementBlockKey = 
                    (LConverted[n - 3] % 2) * 8 +
                    (LConverted[n - 2] % 2) * 4 +
                    (LConverted[n - 1] % 2) * 2 +
                    (LConverted[n] % 2) * 1;
                int replacementBlockValue = replacementVector[replacementBlockKey];
                LConverted[n - 3] = replacementBlockValue / 8;
                replacementBlockValue = replacementBlockValue % 8;
                LConverted[n - 2] = replacementBlockValue / 4;
                replacementBlockValue = replacementBlockValue % 4;
                LConverted[n - 1] = replacementBlockValue / 2;
                replacementBlockValue = replacementBlockValue % 2;
                LConverted[n] = replacementBlockValue / 1;
            }

            string shiftBits = "";
            for (int n = 0; n < 11; n++)
            {
                shiftBits += LConverted[n] % 2;
            }
            for (int n = 0; n < LConverted.size() - 11; n++)
            {
                LConverted[n] = LConverted[n + 11] % 2;
            }
            for (int n = LConverted.size() - 11; n < LConverted.size(); n++)
            {
                LConverted[n] = shiftBits[n - LConverted.size() + 11] % 2;
            }

            for (int n = 0; n < LConverted.size(); n++)
            {
                R[n] = ((R[n] % 2) ^ (LConverted[n] % 2));
            }
        }

        inputBlocksBits[k] = L + R;
    }

    return getEncodeText(inputBlocksBits);
}

int main()
{
    system("chcp 1251");
    srand(time(NULL));

    string inputString = "я играю в компьютерные игры";
    string key = "смешались в кучу кони, люди и за";
    vector<int> replacementVector = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
    random_shuffle(replacementVector.begin(), replacementVector.end());

    cout << "введенная строка: " << inputString << endl;

    string encodingString = encode(inputString, key, replacementVector);
    cout << "закодированная строка: " << encodingString << endl;

    string decodingString = decode(encodingString, key, replacementVector);
    cout << "раскодированная строка: " << decodingString << endl;

    int end;
    cin >> end;
}
