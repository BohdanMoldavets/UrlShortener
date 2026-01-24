import "./trans/i18n";
import { Routes, Route } from "react-router-dom";
import { RedirectPage } from "./shorten/RedirectPage";
import { Shorter } from "./shorten/Shorter";
import { ShorterInfo } from "./shorten/ShorterInfo";
import { AboutPage } from "./about/AboutPage";
import { ThemeProvider } from "./context/ThemeContext";

function App() {
  return (
    <ThemeProvider>
      <Routes>
        <Route path="/" element={<Shorter />} />
        <Route path="/about" element={<AboutPage />} />
        <Route path="/:shortId" element={<RedirectPage />} />
        <Route path="/info" element={<ShorterInfo />} />
      </Routes>
    </ThemeProvider>
  );
}

export default App;
