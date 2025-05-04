import { Link } from 'react-router-dom';

export const Header = () => {
    return (
        <>
            <header className="header">
                <div className="header__logo">
                    <img src="/src/logo/logo.png" alt="logo" />
                </div>
                <div className="header__wrapper">
                    <div className="header__page">
                        <Link to="/" className="header__page-list">Shorten</Link>
                        <Link to="/about" className="header__page-list">About</Link>
                        <Link to="/contact" className="header__page-list">Contacts</Link>
                    </div>
                    <div className="header__contact">
                        <span className="header__mail">supportsomelnk@gmail.com</span>
                        <a href="#"><img src="/src/img/copy.svg" alt="copy" /></a>
                    </div>
                </div>
            </header>

            <div className="header__line"></div>
        </>
    );
};

