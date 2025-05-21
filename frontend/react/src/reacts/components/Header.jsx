import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useTheme } from '../theme/ThemeContext';
import { useTranslation } from 'react-i18next';
import { Panel } from './Panel';
import i18n from 'i18next';
import '../../sass/blocks/header.scss';

export const Header = ({ aboutContactText }) => {
    const { theme } = useTheme();
    const { t } = useTranslation();
    const [panelOpen, setPanelOpen] = useState(false);
    const location = useLocation();

    const isHomePage = location.pathname === '/';
    const isAboutPage = location.pathname === '/about';

    i18n.on('languageChanged', (lng) => {
        document.documentElement.setAttribute('lang', lng);
    });

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
                        <Link to="/" className={`header__page-list ${isHomePage ? 'header__page-list--active' : ''}`}>{t("shorten")}</Link>
                        <Link to="/about" className={`header__page-list ${isAboutPage ? 'header__page-list--active' : ''}`}>{t("about")}</Link>
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
                <button className="header__btn-mobile" onClick={() => setPanelOpen(true)}>Panel</button>
            </header>

            <div className="header__line"></div>
            {panelOpen && (
                <Panel isOpen={panelOpen} onClose={() => setPanelOpen(false)} />
            )}
        </>
    );
};
