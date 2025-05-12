import { useState } from 'react';
import { api } from './api';
import { useTranslation } from 'react-i18next';
import '../sass/blocks/shorten.scss';
import '../sass/blocks/result.scss';
import '../sass/libs/fontello.css';

const LinkShortener = ({ shortUrl, setShortUrl }) => {
    const [url, setUrl] = useState('');
    const [copied, setCopied] = useState(false); // <--- Добавлено
    const { t } = useTranslation();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/v1/urls', {
                long_url: url
            });

            const shortPath = response.data.short_url.split('/').pop();
            setShortUrl(`http://localhost:5173/${shortPath}`);
        } catch (error) {
            console.error('Error while shortening link:', error);
        }
    };

    const handleCopy = () => {
        navigator.clipboard.writeText(shortUrl)
            .then(() => {
                setCopied(true);
                setTimeout(() => setCopied(false), 2000); // скрыть через 2 секунды
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
                            <button type="submit" className='shorten__btn'>{t("compress")}</button>
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
                <section className='result'>
                    <a href={shortUrl} target="_blank" rel="noopener noreferrer" className='result__link'>{shortUrl}</a>
                    <p className='result__text'>
                        Copy completed. You can now easily distribute<br />
                        your short link across any platform or<br />
                        communication channel.
                    </p>
                    <div className="result__btns">
                        <button type="button" className='result__btn-r' onClick={() => setShortUrl('')}>Reject</button>
                        <button type="button" className='result__btn-c' onClick={handleCopy}>Catch</button>
                    </div>
                    {copied && <div className="copied-popup">Copied!</div>}
                </section>
            )}
        </>
    );
};

export default LinkShortener;
