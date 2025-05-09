import { Link } from 'react-router-dom';
import '../sass/blocks/header.scss';
import { useTheme } from './ThemeContext';

export const Header = ({ isAboutPage, aboutContactText }) => {
    const { theme } = useTheme();

    return (
        <>
            <header className="header">
                <div className="header__logo">
                    <Link to="/">
                        <img src={`/src/logo/logo${theme === 'light' ? 'Light' : 'Dark'}.png`} alt="logo" />
                    </Link>
                </div>
                <div className="header__wrapper">
                    <div className="header__page">
                        <Link to="/" className="header__page-list">Shorten</Link>
                        <Link to="/about" className={`header__page-list ${isAboutPage ? 'header__page-list--active' : ''}`}>About</Link>
                    </div>
                    <div className="header__contact">
                        {isAboutPage ? (
                            <span className="header__mail">{aboutContactText}</span>
                        ) : (
                            <>
                                <span className="header__mail">supportsomelnk@gmail.com</span>
                                <span className='icon-clone header__icon'></span>
                            </>
                        )}
                    </div>
                </div>
            </header>

            <div className="header__line"></div>
        </>
    );
};