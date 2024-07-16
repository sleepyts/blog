import {fileURLToPath, URL} from 'node:url'

import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {AntDesignVueResolver} from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  // 根据模式（开发、生产等）加载环境变量
  const env = loadEnv(mode, process.cwd());

  return {
    build: {
      rollupOptions: {
        output: {
          dir: 'dist1',

        }
      }
    },
    plugins: [
      vue(
          {
            template:{
              compilerOptions:{
                isCustomElement: tag=>tag==="meting-js",
              }
            }
          }
      ),
      AutoImport({
        resolvers: [
          AntDesignVueResolver(),
        ],
      }),
      Components({
        resolvers: [
          AntDesignVueResolver({
            importStyle: false, // css in js
          }),
        ],

      }),
    ],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url)),
      },
    },
    server: {
      proxy: {
        '/api': {
          target: env.VITE_API_URL,
          changeOrigin: true,
          rewrite: path => path.replace(/^\/api/, ''),
        },

      },
    },
  };
});
