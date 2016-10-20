var webpack = require('webpack');
var glob = require('glob');
var path = require('path');
var frontend = path.join(__dirname, 'frontend');
var entryFiles = glob.sync(frontend + '/js/**/*.js');

entryFiles.push(path.join(frontend, 'app.js'));
console.log('NODE_ENV is set to ' + process.env.NODE_ENV);

var debug = process.env.NODE_ENV !== 'production';
var DIST_PATH = './src/main/resources/static/dist/';
if (debug) {
  DIST_PATH = './build/resources/main/static/dist';
}

module.exports = {
  devtool: 'eval',
  entry: entryFiles,
  output: {
    path: DIST_PATH,
    filename: 'app.bundle.js'
  },
  module: {
    loaders: [
      {
        test: /\.js$/,
        include: frontend,
        loader: 'babel',
        query: {
          presets: ['es2015']
        }
      },
      {
        test: /\.html$/,
        include: frontend,
        loader: "html"
      },
      {
        test: /\.pug$/,
        include: frontend,
        loader: 'pug'
      },
      {
        test: /\.scss$/,
        include: frontend,
        loaders: ['style', 'css', 'sass']
      }
    ]
  },
  node: {
    fs: 'empty',
    tls: 'empty'
  },
  plugins: debug ? [] : [
    new webpack.optimize.DedupePlugin(),
    new webpack.optimize.OccurrenceOrderPlugin(),
    new webpack.optimize.UglifyJsPlugin()
  ]
};