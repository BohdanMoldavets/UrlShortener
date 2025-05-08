import { Routes, Route } from 'react-router-dom';
import { RedirectPage } from './RedirectPage';
import { Shorter } from './Shorter';
import { About } from './About';


function App() {
  return (
    <Routes>
      <Route path='/' element={<Shorter />} />
      <Route path='/about' element={<About />} />
      <Route path="/:shortId" element={<RedirectPage />} />
    </Routes>
  );
}

export default App;
