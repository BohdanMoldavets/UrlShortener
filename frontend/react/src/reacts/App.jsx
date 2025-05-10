import './i18n';
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { RedirectPage } from './RedirectPage';
import { Shorter } from './Shorter';
import { About } from './About';


import { ThemeProvider } from './ThemeContext';

function App() {
  return (
    <ThemeProvider>
      <Routes>
        <Route path='/' element={<Shorter />} />
        <Route path='/about' element={<About />} />
        <Route path="/:shortId" element={<RedirectPage />} />
      </Routes>
    </ThemeProvider>
  );
}

export default App;