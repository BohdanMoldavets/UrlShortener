import { useState } from 'react';
import { api } from './api';
import { useTranslation } from 'react-i18next';
import '../sass/blocks/shorten.scss';
import '../sass/libs/fontello.css';


const LinkShortener = () => {
    const [url, setUrl] = useState('');
    const [shortUrl, setShortUrl] = useState('');
    const { t } = useTranslation();

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await api.post('/v1/urls', {
                long_url: url
            });

            const shortPath = response.data.short_url.split('/').pop();
            console.log(shortPath);
            setShortUrl(`http://localhost:5173/${shortPath}`);
        } catch (error) {
            console.error('Error while shortening link:', error);
        }
    };

    return (
        <div className='shorten'>
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

            {shortUrl && (
                <div>
                    <p>Shortened link:</p>
                    <a href={shortUrl} target="_blank" rel="noopener noreferrer">{shortUrl}</a>
                </div>
            )}

        </div>
    );
};

export default LinkShortener;

