import { Link } from 'react-router-dom';
import { useTheme } from '../theme/ThemeContext';
import { useTranslation } from 'react-i18next';
import '../../sass/blocks/panel.scss';

export const Panel = ({ isOpen, onClose }) => {
    const { theme, toggleTheme } = useTheme();
    const { i18n, t } = useTranslation();
    const currentLang = i18n.language;

    const toggleLanguage = () => {
        const newLang = currentLang === 'en' ? 'pl' : 'en';
        i18n.changeLanguage(newLang);
    };

    return (
        <div className={`panel ${isOpen ? 'open' : ''}`}>
            <button className="panel__close" onClick={onClose}>×</button>
            <nav className="panel__nav">
                <Link to="/" onClick={onClose}>{t("shorten")}</Link>
                <Link to="/about" onClick={onClose}>{t("about")}</Link>
            </nav>
            <div className="panel__controls">
                <div className="panel__language" onClick={toggleLanguage}>
                    <div className={`panel__language-option-EN ${currentLang === 'en' ? 'active' : ''}`}>EN</div>
                    <div className={`panel__language-option-PL ${currentLang === 'pl' ? 'active' : ''}`}>PL</div>
                    <div className="panel__language-slider" style={{ transform: `translateX(${currentLang === 'pl' ? '100%' : '0'})` }}></div>
                </div>
                <div className="panel__theme">
                    <span className={`icon-sun panel__sun ${theme === 'light' ? 'active' : ''}`} onClick={() => toggleTheme('light')}></span>
                    <span className={`icon-moon-inv panel__moon ${theme === 'dark' ? 'active' : ''}`} onClick={() => toggleTheme('dark')}></span>
                </div>
            </div>
        </div >
    );
};
