import React, { useState } from 'react';
import '../sass/blocks/footer.scss';
import '../sass/libs/fontello.css';
import { useTheme } from './ThemeContext';

export const Footer = () => {
    const [language, setLanguage] = useState('En');
    const { theme, toggleTheme } = useTheme();

    const toggleLanguage = () => {
        setLanguage(prevLang => (prevLang === 'En' ? 'Pl' : 'En'));
        // Здесь можно добавить логику смены языка в приложении
    };

    return (
        <>
            <div className='footer__line'></div>
            <footer className='footer'>
                <div className="footer__language-switcher" onClick={toggleLanguage}>
                    <div className={`footer__language-option-EN ${language === 'En' ? 'active' : ''}`}>En</div>
                    <div className={`footer__language-option-PL ${language === 'Pl' ? 'active' : ''}`}>Pl</div>
                    <div className="footer__language-slider" style={{ transform: `translateX(${language === 'Pl' ? '100%' : '0'})` }}></div>
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
                        FlowLnk Simplify and Share your Links 2025
                    </div>
                </div>
            </footer>
        </>
    );
};