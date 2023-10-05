#include <iostream>
#include <vector>

using namespace std;

string encode(string alphabet, string inputString, string key) {
    string encodingString = "";

    int index = 0;
    for (int i = 0; i < inputString.length(); i++)
    {
        if (inputString[i] != ' ') 
        {
            int offsetLine = alphabet.find(inputString[i]);
            int offsetColumn = alphabet.find(key[index % key.length()]);

            encodingString += alphabet.at((offsetLine + offsetColumn) % 33);
            ++index;
        }
        else {
            encodingString += ' ';
        }
    }
    return encodingString;
}

string decode(string alphabet, string encodingString, string key) {
    string decodingString = "";

    int index = 0;
    for (int i = 0; i < encodingString.length(); i++)
    {
        if (encodingString[i] != ' ')
        {
            int offsetKey = 33 - alphabet.find(key[index % key.length()]);
            int offsetEncode = alphabet.find(encodingString[i]);

            decodingString += alphabet.at((offsetEncode + offsetKey) % 33);
            ++index;
        }
        else {
            decodingString += ' ';
        }
    }
    return decodingString;
}

int main()
{
    system("chcp 1251");
    srand(time(NULL));

    string alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    string inputString = "я играю в компьютерные игры";
    string key = "чёрт";

    string encodingString = encode(alphabet, inputString, key);
    cout << encodingString << endl;
    string decodingString = decode(alphabet, encodingString, key);
    cout << decodingString;

}
