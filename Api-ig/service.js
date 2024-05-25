const express = require('express');
const cors = require('cors');
const axios = require('axios');

const app = express();
app.use(cors())

const accessToken = 'IGQWRPaGpsMFJFTGFNSVUtb2ZA5bG82bnJMQ0ZAfcVF6RmF3ZADFhVGdpOG42U0Y1QWFtRzUydk1zQTR1MGFWemtvQi1sLTVQYVZADZAEVhVEE5T1E3MjdOV0RpZATI1QzlEbmdyVXppVzJCaDg5QQZDZD';

app.get('/profile', async (req, res) => {
    try {
        const profileResponse = await axios.get(`https://graph.instagram.com/me?fields=id,username,account_type,media_count&access_token=${accessToken}`);
        const profile = profileResponse.data;
        res.json(profile);
    } catch (error) {
        res.status(500).send('Error fetching Instagram profile');
    }
});
app.get('/media', async (req, res) => {
    try {
        const mediaResponse = await axios.get(`https://graph.instagram.com/me/media?fields=id,caption,media_url,media_type,timestamp,children{media_url}&access_token=${accessToken}`);
        const media = mediaResponse.data;

        res.json(media);
    } catch (error) {
        res.status(500).send('Error fetching Instagram media');
    }
});

app.listen(3002, () => {
    console.log('Server started on http://localhost:3002');
});
