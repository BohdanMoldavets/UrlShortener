import { useState } from 'react';
import { Header } from './Header';
import { Footer } from './Footer';
import LinkShortener from './LinkShortener';

export const Shorter = () => {
    const [shortUrl, setShortUrl] = useState('');

    return (
        <>
            <Header />
            <div>
                <LinkShortener shortUrl={shortUrl} setShortUrl={setShortUrl} />
            </div>
            {!shortUrl && <Footer />}
        </>
    );
};
