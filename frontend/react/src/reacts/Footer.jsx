import React from 'react';
import '../sass/blocks/footer.scss';
import '../sass/libs/fontello.css';
import { useTheme } from './ThemeContext';
import { useTranslation } from 'react-i18next';

export const Footer = () => {
    const { theme, toggleTheme } = useTheme();
    const { i18n } = useTranslation();
    const currentLang = i18n.language;
    const { t } = useTranslation();

    const toggleLanguage = () => {
        const newLang = currentLang === 'en' ? 'pl' : 'en';
        i18n.changeLanguage(newLang);
    };

    return (
        <>
            <div className='footer__line'></div>
            <footer className='footer'>
                <div className="footer__language-switcher" onClick={toggleLanguage}>
                    <div className={`footer__language-option-EN ${currentLang === 'en' ? 'active' : ''}`}>En</div>
                    <div className={`footer__language-option-PL ${currentLang === 'pl' ? 'active' : ''}`}>Pl</div>
                    <div className="footer__language-slider" style={{ transform: `translateX(${currentLang === 'pl' ? '100%' : '0'})` }}></div>
                </div>
                <div className="footer__icons">
                    <span className={`icon-sun footer__sun ${theme === 'light' ? 'active' : ''}`} onClick={() => toggleTheme('light')}></span>
                    <span className={`icon-moon-inv footer__moon ${theme === 'dark' ? 'active' : ''}`} onClick={() => toggleTheme('dark')}></span>
                </div>
                <div className="footer__copyright">
                    <div className='footer__logo'>
                        <img src={`/src/logo/logoFooter${theme === 'light' ? 'Light' : 'Dark'}.png`} alt="logo" />
                    </div>
                    <span className='icon-copyright footer__copyright'></span>
                    <div className='footer__text'>
                        {t("footer")}
                    </div>
                </div>
            </footer>
        </>
    );
};
