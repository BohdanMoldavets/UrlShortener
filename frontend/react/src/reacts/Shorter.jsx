import { Header } from './Header';
import { Footer } from './Footer';
import LinkShortener from './LinkShortener';

export const Shorter = () => {
    return (
        <>
            <Header />
            <div>
                <LinkShortener />
            </div>
            <Footer />
        </>
    );
};
