import '../sass/blocks/footer.scss';

export const AboutPage = () => {
    return (
        <>
            <section className="about">
                <h2 className="about__title">Our platform offers an instant <br /> and seamless way <br />to shorten links without <br />unnecessary <br />action.</h2>
                <div className="about__info">
                    <div className="about__info-text">
                        <h3>Privacy without compromise</h3>
                        <p>All links are processed securely with no data storage, ensuring that personal and usage information remains completely confidential. Our system is designed to protect your privacy at every stage.</p>
                    </div>
                    <div className="about__info-photo">
                        <div className="about__info-card-1">
                            <img src="/src/img/bohdan.png" alt="photo-1" />
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
                            <img src="/src/img/bohdan.png" alt="photo-3" />
                            <div className="about__info-card-3-text">
                                <h3>Front-End Developer</h3>
                                <p>Bohdan Moldavets</p>
                            </div>
                        </div>
                        <div className="about__info-card-4">
                            <img src="/src/img/bohdan.png" alt="photo-4" />
                            <div className="about__info-card-4-text">
                                <h3>Front-End Developer</h3>
                                <p>Bohdan Moldavets</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </>
    );
}