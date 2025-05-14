import { useState } from 'react';
import { Trans } from 'react-i18next';
import { api } from './api';
import { useTranslation } from 'react-i18next';
import '../sass/blocks/shorten.scss';
import '../sass/blocks/result.scss';
import '../sass/libs/fontello.css';

const LinkShortener = ({ shortUrl, setShortUrl }) => {
    const [url, setUrl] = useState('');
    const [copied, setCopied] = useState(false);
    const [isResultVisible, setIsResultVisible] = useState(false);
    const { t } = useTranslation();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/v1/urls', {
                long_url: url
            });

            const shortPath = response.data.short_url.split('/').pop();
            setShortUrl(`http://localhost:5173/${shortPath}`);
            setIsResultVisible(true);
        } catch (error) {
            console.error('Error while shortening link:', error);
        }
    };

    const handleBack = () => {
        setIsResultVisible(false);
        setTimeout(() => {
            setShortUrl('');
        }, 500);
    };

    const handleCopy = () => {
        navigator.clipboard.writeText(shortUrl)
            .then(() => {
                setCopied(true);
                setTimeout(() => setCopied(false), 2000);
            })
            .catch(err => {
                console.error('Failed to copy: ', err);
            });
    };

    return (
        <>
            <div className='shorten'>
                {!shortUrl && (
                    <>
                        <h1 className='shorten__title'>enter:your:link</h1>

                        <form onSubmit={handleSubmit} className='shorten__form'>
                            <input
                                type="url"
                                value={url}
                                onChange={(e) => setUrl(e.target.value)}
                                placeholder="enter:your:link"
                                required
                                className='shorten__input'
                            />
                            <button type="submit" className='btn-shorten shorten__btn'>{t("compress")}</button>
                        </form>

                        <h2 className='shorten__title-h2'>{t("enterText")}</h2>

                        <div className="shorten__info">
                            <div className="shorten__location">
                                <span className="icon-map-pin shorten__map"></span>
                                <p>Poznan, Poland</p>
                            </div>
                            <div className="shorten__work">
                                <p>{t("list")}</p>
                                <ul>
                                    <li>{t("itemOne")}</li>
                                    <li>{t("itemTwo")}</li>
                                    <li>{t("itemThree")}</li>
                                    <li>{t("itemFour")}</li>
                                </ul>
                            </div>
                        </div>
                    </>
                )}
            </div>

            {shortUrl && (
                <section className={`result ${isResultVisible ? 'result--visible' : 'result--hide'}`}>
                    <div className="result__wrapper">
                        <a href={shortUrl} target="_blank" rel="noopener noreferrer" className='result__link' onClick={handleCopy}>{shortUrl}</a>
                        <span className='icon-clone result__icon' onClick={handleCopy}></span>
                    </div>
                    <p className='result__text'>
                        <Trans
                            i18nKey="result"
                            components={[<></>, <br />, <br />]}
                        />
                    </p>
                    <div className="result__btns">
                        <button type="button" className='btn-back result__btn-back' onClick={handleBack}>{t("back")}</button>
                        <button type="button" className='btn-info result__btn-info' onClick={handleCopy}>Info</button>
                    </div>
                    {copied && <div className="copied-popup">{t("copy")}</div>}
                </section>
            )}
        </>
    );
};

export default LinkShortener;
