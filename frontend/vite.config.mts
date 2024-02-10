import { resolve } from 'path';
import { defineConfig } from 'vite';

export default defineConfig({
  build: {
    lib: {
      entry: [
        resolve(__dirname, 'src', 'entries', 'main.ts'),
      ],
      formats: ['es'],
      fileName: (_, entryName) => `${entryName}.js`,
    },
    outDir: resolve(__dirname, '..', 'public', 'javascripts'),
  },
  resolve: {
    alias: {
      '#': resolve(__dirname, 'src'),
    },
  },
});
