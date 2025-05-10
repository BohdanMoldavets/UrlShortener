import { useTranslation } from 'react-i18next';
import { Trans } from 'react-i18next';
import '../sass/blocks/footer.scss';

export const AboutPage = () => {
    const { t } = useTranslation();
    return (
        <>
            <section className="about">
                <h2 className="about__title">
                    <Trans
                        i18nKey="aboutTitle"
                        components={[<></>, <br />, <br />, <br />, <br />]}
                    />
                </h2>
                <div className="about__info">
                    <div className="about__info-text">
                        <h3>{t("aboutSubTitle")}</h3>
                        <p>{t("aboutText")}</p>
                    </div>
                    <div className="about__info-photo">
                        <div className="about__info-card-1">
                            <img src="/src/img/bohdan.jpg" alt="photo-1" />
                            <div className="about__info-card-1-text">
                                <h3>Front-End Developer</h3>
                                <p>Bohdan Moldavets</p>
                            </div>
                        </div>
                        <div className="about__info-card-2">
                            <img src="/src/img/anna.png" alt="photo-2" />
                            <div className="about__info-card-2-text">
                                <h3>Front-End Developer</h3>
                                <p>Bohdan Moldavets</p>
                            </div>
                        </div>
                        <div className="about__info-card-3">
                            <img src="/src/img/stas.jpg" alt="photo-3" />
                            <div className="about__info-card-3-text">
                                <h3>Front-End Developer</h3>
                                <p>Stanislav Riabtsev</p>
                            </div>
                        </div>
                        <div className="about__info-card-4">
                            <img src="/src/img/ivan.jpg" alt="photo-4" />
                            <div className="about__info-card-4-text">
                                <h3>Front-End Developer</h3>
                                <p>Ivan Zamishchak</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </>
    );
}