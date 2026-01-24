import "./i18n/i18n";
import { Routes, Route } from "react-router-dom";
import { RedirectPage } from "./pages/shorten/RedirectPage";
import { ShorterPage } from "./pages/shorten/ShorterPage";
import { ShorterInfo } from "./pages/shorten/ShorterInfo";
import { AboutPage } from "./pages/about/AboutPage";
import { ThemeProvider } from "./context/ThemeContext";

function App() {
  return (
    <ThemeProvider>
      <Routes>
        <Route path="/" element={<ShorterPage />} />
        <Route path="/about" element={<AboutPage />} />
        <Route path="/:shortId" element={<RedirectPage />} />
        <Route path="/info" element={<ShorterInfo />} />
      </Routes>
    </ThemeProvider>
  );
}

export default App;
