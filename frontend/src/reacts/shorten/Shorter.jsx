import { useState } from 'react';
import { Header } from '../components/Header';
import { Footer } from '../components/Footer';
import { ShorterPage } from './ShorterPage';
import LinkShortener from './LinkShortener';

export const Shorter = () => {
    const [shortUrl, setShortUrl] = useState('');

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
