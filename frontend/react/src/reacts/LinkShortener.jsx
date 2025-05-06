import { useState } from 'react';
import { api } from './api';
import '../sass/blocks/shorten.scss';


const LinkShortener = () => {
    const [url, setUrl] = useState('');
    const [shortUrl, setShortUrl] = useState('');

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
            <h2 className='shorten__title-h2'>Enter your link, shorten it in seconds, and share with anyone. No distractions, no unnecessary steps — just fast and clean results.</h2>
            <div className="shorten__info">
                <p>Instant action, smooth flow</p>
                <ul>
                    <li>No account creation required.</li>
                    <li>Enter your link and get the short version at once.</li>
                    <li>All links are processed securely without being stored.  </li>
                    <li>Minimal interface for fast and distraction free shortening.</li>
                </ul>
            </div>
            <form onSubmit={handleSubmit} className='shorten__form'>
                <input
                    type="url"
                    value={url}
                    onChange={(e) => setUrl(e.target.value)}
                    placeholder="Insert link"
                    required
                    className='shorten__input'
                />
                <button type="submit" className='shorten__btn'>Compress</button>
            </form>

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

