/** @typedef { import('prettier').Config } PrettierConfig */
/** @typedef { import('prettier-plugin-tailwindcss').PluginOptions } TailwindConfig */
/** @typedef { import('@ianvs/prettier-plugin-sort-imports').PluginConfig } SortImportsConfig */

/** @type { PrettierConfig | SortImportsConfig | TailwindConfig } */
const config = {
  jsxSingleQuote: true,
  singleQuote: true,
  trailingComma: 'all',
  plugins: [
    '@ianvs/prettier-plugin-sort-imports',
    'prettier-plugin-tailwindcss',
  ],
  importOrder: ['<THIRD_PARTY_MODULES>', '', '^#/', '^[../]', '^[./]'],
  importOrderParserPlugins: ['typescript', 'jsx', 'decorators-legacy'],
  importOrderTypeScriptVersion: '4.4.0',
};

export default config;
