import { Trans, useTranslation } from 'react-i18next';
import i18n from 'i18next';
import { useNavigate } from 'react-router-dom';
import '../../sass/blocks/shorten.scss';
import '../../sass/blocks/result.scss';
import '../../sass/libs/fontello.css';

export const ShorterPage = ({
    url,
    setUrl,
    shortUrl,
    copied,
    isResultVisible,
    handleSubmit,
    handleBack,
    handleCopy,
}) => {
    const { t } = useTranslation();
    i18n.on('languageChanged', (lng) => {
        document.documentElement.setAttribute('lang', lng);
    });
    const navigate = useNavigate();

    const handleInfoClick = () => {
        navigate(`/info?url=${encodeURIComponent(shortUrl)}`);
    };

    return (
        <>
            <div className='shorten'>
                {!shortUrl && (
                    <>
                        <h1 className='title-shorten shorten__title'>enter:your:link</h1>

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

                        <h2 className="title-shorten-h2 shorten__title-h2">{t("enterText")}</h2>

                        <div className="shorten__info">
                            <div className="text-location shorten__location">
                                <span className="icon-map-pin shorten__map"></span>
                                <p>Poznan, Poland</p>
                            </div>
                            <div className="shorten__work">
                                <p className='list'>{t("list")}</p>
                                <ul className='list'>
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
                        <a href={shortUrl} className='title-result result__link' onClick={handleCopy}>{shortUrl}</a>
                        <span className='icon-clone result__icon' onClick={handleCopy}></span>
                    </div>
                    <p className='result__text'>
                        <Trans i18nKey="result" components={[<></>, <br />, <br />]} />
                    </p>
                    <div className="result__btns">
                        <button type="button" className='btn-back result__btn-back' onClick={handleBack}>{t("back")}</button>
                        <button type="button" className='btn-info result__btn-info' onClick={handleInfoClick}>Info</button>
                    </div>
                    {copied && <div className="copied-popup">{t("copy")}</div>}
                </section>
            )}
        </>
    );
};
