const a = "abcdefghijklmnopqrstuvwxyz".split(""), c = (r, s) => {
  let e = r + s;
  return e > 25 && (e = e - 26), e < 0 && (e = 26 + e), e;
};
window.caesarCipher = (r, s) => {
  s = s % 26;
  const e = r.toLowerCase();
  let o = "";
  return Array.from(e).forEach((n, d) => {
    if (n === " ") {
      o += n;
      return;
    }
    const t = a.indexOf(n), f = c(t, s);
    r[d] === r[d].toUpperCase() ? o += a[f].toUpperCase() : o += a[f];
  }), alert(o);
};
