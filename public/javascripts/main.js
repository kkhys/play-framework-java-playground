const i = "abcdefghijklmnopqrstuvwxyz".split(""), d = (e, n) => {
  let t = e + n;
  return t > 25 && (t = t - 26), t < 0 && (t = 26 + t), t;
};
window.convertCaesarCipher = () => {
  const e = prompt("Enter a string to be shifted") || "";
  if (!e)
    return alert("You must enter a string to be shifted");
  let n = parseInt(prompt("Enter a shift amount") || "");
  if (isNaN(n))
    return alert("You must enter a valid shift amount");
  n = n % 26;
  const t = e.toLowerCase();
  let r = "";
  return Array.from(t).forEach((s, o) => {
    if (s === " ") {
      r += s;
      return;
    }
    const f = i.indexOf(s), a = d(f, n);
    e[o] === e[o].toUpperCase() ? r += i[a].toUpperCase() : r += i[a];
  }), alert(r);
};
