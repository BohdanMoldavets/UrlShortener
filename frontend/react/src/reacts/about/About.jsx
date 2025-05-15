import { Header } from '../components/Header';
import { Footer } from '../components/Footer';
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