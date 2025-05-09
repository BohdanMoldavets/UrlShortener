import { Header } from './Header';
import { Footer } from './Footer';
import { AboutPage } from './AboutPage';

export const About = () => {
    return (
        <>
            <Header isAboutPage={true} aboutContactText="Terms & Conditions" />
            <AboutPage />
            <Footer />
        </>
    );
};