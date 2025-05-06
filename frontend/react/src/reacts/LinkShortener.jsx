import { useState } from 'react';
import { api } from './api';

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
        <div>
            <form onSubmit={handleSubmit}>
                <input
                    type="url"
                    value={url}
                    onChange={(e) => setUrl(e.target.value)}
                    placeholder="Insert link"
                    required
                    className='form__input'
                />
                <button type="submit" className='form__btn'>Reduce</button>
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

