const ALPHABET = 'abcdefghijklmnopqrstuvwxyz'.split('');

const getShiftedIndex = (currentIndex: number, shiftAmount: number): number => {
  let newIndex = currentIndex + shiftAmount;
  if (newIndex > 25) newIndex = newIndex - 26;
  if (newIndex < 0) newIndex = 26 + newIndex;
  return newIndex;
};

window.caesarCipher = (inputString: string, shiftAmount: number) => {
  shiftAmount = shiftAmount % 26;
  const lowerCaseString = inputString.toLowerCase();
  let shiftedString = '';

  Array.from(lowerCaseString).forEach((currentLetter, i) => {
    if (currentLetter === ' ') {
      shiftedString += currentLetter;
      return;
    }

    const currentIndex = ALPHABET.indexOf(currentLetter);
    const shiftedIndex = getShiftedIndex(currentIndex, shiftAmount);

    if (inputString[i] === inputString[i].toUpperCase()) {
      shiftedString += ALPHABET[shiftedIndex].toUpperCase();
    } else shiftedString += ALPHABET[shiftedIndex];
  });

  return alert(shiftedString);
};
