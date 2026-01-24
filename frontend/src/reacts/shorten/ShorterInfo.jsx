import { useLocation } from 'react-router-dom';
import { Header } from '../components/Header';
import { Footer } from '../components/Footer';
import { useShortLinkInfo } from './ShortLinkInfo';
import { useTranslation } from 'react-i18next';
import '../../sass/blocks/info.scss';

export const ShorterInfo = () => {
    const { t } = useTranslation();
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const shortUrl = searchParams.get('url');

    const { info, error, loading } = useShortLinkInfo(shortUrl, true);

    return (
        <>
            <Header isAboutPage={true} aboutContactText="Terms & Conditions" />
            <section className='info'>
                <h2 className='title-info info__title'> {t("info")}
                    <div>Info about link</div>
                    <div>Info about link</div>
                    <div>Info about link</div>
                    <div>Info about link</div>
                    <div>Info about link</div>
                </h2>
                {!shortUrl && <p>No short URL provided.</p>}
                {loading && <p>Loading info...</p>}
                {error && <p style={{ color: 'red' }}>{error}</p>}
                {info && (
                    <div className="info__list">
                        <ul>
                            <li className='info__item'>{t("long")} : <a href={info.long_url} target="_blank" rel="noopener noreferrer" title={info.long_url}>{info.long_url || 'N/A'}</a></li>
                            <li className='info__item'>{t("short")} : <span>{info.short_url || 'N/A'}</span></li>
                            <li className='info__item'>{t("date")} : <span>{info.expires_date ? new Date(info.expires_date).toLocaleString() : 'N/A'}</span></li>
                            <li className='info__item'>{t("status")} : <span>{info.link_status || 'N/A'}</span></li>
                            <li className='info__item'>{t("click")} : <span>{info.total_clicks ?? 'N/A'}</span></li>
                        </ul>
                    </div>
                )}
            </section>
            <Footer />
        </>
    );
};
