import React, { useState } from 'react';
import '../sass/blocks/footer.scss';
import '../sass/libs/fontello.css';

export const Footer = () => {
    const [language, setLanguage] = useState('En');

    const toggleLanguage = () => {
        setLanguage(prevLang => (prevLang === 'En' ? 'Pl' : 'En'));
        // Здесь можно добавить логику смены языка в приложении и изминения темы
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
                    <span className='icon-sun footer__sun'></span>
                    <span className='icon-moon-inv footer__moon'></span>
                </div>
                <div className="footer__copyright">
                    <div className='footer__logo'>
                        <img src="/src/logo/logoFooterDark.png" alt="logo" />
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