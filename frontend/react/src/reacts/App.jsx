import { Routes, Route, Link } from 'react-router-dom';
import { Home } from './Home';
import { About } from './About';
import { Contact } from './Contact';

function App() {
  return (
    <>
      <header>
        <Link to="/">Home</Link>
        <Link to="/contact">Blog</Link>
        <Link to="/about">About</Link>
      </header>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/about' element={<About />} />
        <Route path='/contact' element={<Contact />} />
      </Routes>
    </>
  );
}

export default App