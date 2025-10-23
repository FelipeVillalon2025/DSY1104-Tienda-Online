module.exports = function(config) {
  config.set({
    // Ejecutor de pruebas y mapeo de fuentes
    frameworks: ['jasmine', 'webpack'], 

    plugins: [
        'karma-webpack',
        'karma-jasmine',
        'karma-chrome-launcher',
        'karma-sourcemap-loader'
    ],

    // Archivos de prueba: Busca cualquier archivo que termine en .test.js
    files: [
      'src/**/*.test.js', 
    ],

    // Pre-procesadores: Aplica Webpack y Sourcemap
    preprocessors: {
      'src/**/*.js': ['webpack', 'sourcemap'],
      'src/**/*.jsx': ['webpack', 'sourcemap'],
      'src/**/*.test.js': ['webpack', 'sourcemap'],
    },

    // Configuración de Webpack (¡Añadimos reglas para CSS!)
    webpack: {
      mode: 'development',
      module: {
        rules: [
          // Regla existente para JS/JSX (React)
          {
            test: /\.(js|jsx)$/,
            exclude: /node_modules/,
            use: {
              loader: 'babel-loader',
              options: {
                presets: ['@babel/preset-env', '@babel/preset-react']
              }
            }
          },
          // REGLA NUEVA PARA CSS
          {
            test: /\.css$/,
            // Usamos 'style-loader' y 'css-loader' para importar y aplicar estilos
            use: ['style-loader', 'css-loader'] 
          }
        ]
      },
      resolve: {
          extensions: ['.js', '.jsx', '.css'] // ¡Añadimos .css para la resolución!
      }
    },
    
    // Ejecutar pruebas en el navegador Chrome
    browsers: ['Chrome'],

    // Reportes de salida
    reporters: ['progress'],

    // Activar vigilancia de archivos para re-ejecutar al guardar
    autoWatch: true,
    
    // Mantener Karma activo
    singleRun: false 

  });
};