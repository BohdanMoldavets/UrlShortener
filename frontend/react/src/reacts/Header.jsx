import { Link } from 'react-router-dom';

export const Header = () => {
    return (
        <>
            <header className="header">
                <div className="header__logo">
                    <img src="/src/logo/logoDark.png" alt="logo" />
                </div>
                <div className="header__wrapper">
                    <div className="header__page">
                        <Link to="/" className="header__page-list">Shorten</Link>
                        <Link to="/about" className="header__page-list">About</Link>
                    </div>
                    <div className="header__contact">
                        <span className="header__mail">supportsomelnk@gmail.com</span>
                        <span className='icon-clone header__icon'></span>
                    </div>
                </div>
            </header>

            <div className="header__line"></div>
        </>
    );
};

