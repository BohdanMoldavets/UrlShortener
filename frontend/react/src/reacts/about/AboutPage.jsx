import { useTranslation } from 'react-i18next';
import { Trans } from 'react-i18next';
import { useMediaQuery } from 'react-responsive';
import '../../sass/blocks/footer.scss';

export const AboutPage = () => {
    const { t } = useTranslation();
    const isMobile = useMediaQuery({ maxWidth: 768 });
    return (
        <>
            <section className="about">
                <h2 className="title-about about__title">
                    <Trans
                        i18nKey="aboutTitle"
                        components={isMobile
                            ? [<></>, <></>, <></>, <></>, <br />]
                            : [<></>, <br />, <br />, <br />, <br />]}
                    />
                </h2>
                <div className="about__info">
                    <div className="about__info-text">
                        <h3 className='text-about'>{t("aboutSubTitle")}</h3>
                        <p className='text-about'>{t("aboutText")}</p>
                    </div>
                    <div className="about__info-photo">
                        <div className="about__info-card-1">
                            <picture>
                                <source media="(max-width: 375px)" srcSet="/src/img/bohdanPhone.jpg" />
                                <img src="/src/img/bohdan.jpg" alt="photo-1" />
                            </picture>
                            <div className="about__info-card-1-text">
                                <h3 className='text-about'>Backend Developer</h3>
                                <p className='text-about'>Bohdan Moldavets</p>
                            </div>
                        </div>
                        <div className="about__info-card-2">
                            <picture>
                                <source media="(max-width: 375px)" srcSet="/src/img/annaPhone.jpg" />
                                <img src="/src/img/anna.png" alt="photo-2" />
                            </picture>
                            <div className="about__info-card-2-text">
                                <h3 className='text-about'>UX/UI Designer</h3>
                                <p className='text-about'>Anna Fenko</p>
                            </div>
                        </div>
                        <div className="about__info-card-3">
                            <picture>
                                <source media="(max-width: 375px)" srcSet="/src/img/stasPhone.jpg" />
                                <img src="/src/img/stas.jpg" alt="photo-2" />
                            </picture>
                            <div className="about__info-card-3-text">
                                <h3 className='text-about'>Frontend Developer</h3>
                                <p className='text-about'>Stanislav Riabtsev</p>
                            </div>
                        </div>
                        <div className="about__info-card-4">
                            <picture>
                                <source media="(max-width: 375px)" srcSet="/src/img/ivanPhone.jpg" />
                                <img src="/src/img/ivan.jpg" alt="photo-2" />
                            </picture>
                            <div className="about__info-card-4-text">
                                <h3 className='text-about'>DevOps Engineer</h3>
                                <p className='text-about'>Ivan Zamishchak</p>
                            </div>
                        </div>
                    </div>
                </div >
            </section >
        </>
    );
}