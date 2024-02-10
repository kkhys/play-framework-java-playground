const ALPHABET = 'abcdefghijklmnopqrstuvwxyz'.split('');

/**
 * Calculates the shifted index based on the current index and shift amount.
 *
 * @param currentIndex - The current index.
 * @param shiftAmount - The amount to shift the index.
 * @returns The shifted index.
 */
const getShiftedIndex = (currentIndex: number, shiftAmount: number): number => {
  let newIndex = currentIndex + shiftAmount;
  if (newIndex > 25) newIndex = newIndex - 26;
  if (newIndex < 0) newIndex = 26 + newIndex;
  return newIndex;
};

/**
 * Converts a string using the Caesar cipher algorithm.
 *
 * @returns The converted string.
 */
window.convertCaesarCipher = () => {
  const inputString = prompt('Enter a string to be shifted') || '';
  if (!inputString) return alert('You must enter a string to be shifted');

  let shiftAmount = parseInt(prompt('Enter a shift amount') || '');
  if (isNaN(shiftAmount)) return alert('You must enter a valid shift amount');

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
