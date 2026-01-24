import { useState } from "react";
import { Header } from "../layout/Header";
import { Footer } from "../layout/Footer";
import { ShorterPage } from "./ShorterPage";
import LinkShortener from "./LinkShortener";

export const Shorter = () => {
  const [shortUrl, setShortUrl] = useState("");

  return (
    <>
      <Header />
      <main>
        <LinkShortener shortUrl={shortUrl} setShortUrl={setShortUrl}>
          {(props) => <ShorterPage {...props} />}
        </LinkShortener>
      </main>
      {!shortUrl && <Footer />}
    </>
  );
};
