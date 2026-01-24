import { useTranslation, Trans } from "react-i18next";
import { useMediaQuery } from "react-responsive";
import { Header } from "../../layout/Header";
import { Footer } from "../../layout/Footer";
import "../../sass/blocks/footer.scss";

import bohdanImg from "../../assets/img/bohdan.jpg";
import bohdanPhoneImg from "../../assets/img/bohdanPhone.jpg";
import annaImg from "../../assets/img/anna.png";
import annaPhoneImg from "../../assets/img/annaPhone.jpg";
import stasImg from "../../assets/img/stas.jpg";
import stasPhoneImg from "../../assets/img/stasPhone.jpg";
import ivanImg from "../../assets/img/ivan.jpg";
import ivanPhoneImg from "../../assets/img/ivanPhone.jpg";

export const AboutPage = () => {
  const { t } = useTranslation();
  const isMobile = useMediaQuery({ maxWidth: 768 });

  return (
    <>
      <Header isAboutPage={true} aboutContactText="Terms & Conditions" />

      <section className="about">
        <h2 className="title-about about__title">
          <Trans
            i18nKey="aboutTitle"
            components={
              isMobile
                ? [<></>, <></>, <></>, <></>, <br />]
                : [<></>, <br />, <br />, <br />, <br />]
            }
          />
        </h2>
        <div className="about__info">
          <div className="about__info-text">
            <h3 className="text-about">{t("aboutSubTitle")}</h3>
            <p className="text-about">{t("aboutText")}</p>
          </div>
          <div className="about__info-photo">
            <div className="about__info-card-1">
              <picture>
                <source media="(max-width: 375px)" srcSet={bohdanPhoneImg} />
                <source media="(max-width: 425px)" srcSet={bohdanPhoneImg} />
                <img src={bohdanImg} alt="Backend Developer" />
              </picture>
              <div className="about__info-card-1-text">
                <h3 className="text-about">Backend Developer</h3>
                <p className="text-about">Bohdan Moldavets</p>
              </div>
            </div>
            <div className="about__info-card-2">
              <picture>
                <source media="(max-width: 375px)" srcSet={annaPhoneImg} />
                <source media="(max-width: 425px)" srcSet={annaPhoneImg} />
                <img src={annaImg} alt="UX/UI Designer" />
              </picture>
              <div className="about__info-card-2-text">
                <h3 className="text-about">UX/UI Designer</h3>
                <p className="text-about">Anna Fenko</p>
              </div>
            </div>
            <div className="about__info-card-3">
              <picture>
                <source media="(max-width: 375px)" srcSet={stasPhoneImg} />
                <source media="(max-width: 425px)" srcSet={stasPhoneImg} />
                <img src={stasImg} alt="Frontend Developer" />
              </picture>
              <div className="about__info-card-3-text">
                <h3 className="text-about">Frontend Developer</h3>
                <p className="text-about">Stanislav Riabtsev</p>
              </div>
            </div>
            <div className="about__info-card-4">
              <picture>
                <source media="(max-width: 375px)" srcSet={ivanPhoneImg} />
                <source media="(max-width: 425px)" srcSet={ivanPhoneImg} />
                <img src={ivanImg} alt="DevOps Engineer" />
              </picture>
              <div className="about__info-card-4-text">
                <h3 className="text-about">DevOps Engineer</h3>
                <p className="text-about">Ivan Zamishchak</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      <Footer />
    </>
  );
};
