import './trans/i18n';
import { Routes, Route } from 'react-router-dom';
import { RedirectPage } from './shorten/RedirectPage';
import { Shorter } from './shorten/Shorter';
import { About } from './about/About';
import { ThemeProvider } from './theme/ThemeContext';

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