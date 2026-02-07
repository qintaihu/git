# Frontend README for Vue 3 + Element Plus

## Prerequisites

- Node.js 16+
- npm or yarn

## Quick Start

### Development

1. **Install dependencies**
   ```bash
   cd frontend
   npm install
   ```

2. **Run development server**
   ```bash
   npm run dev
   ```
   
   The frontend will be available at `http://localhost:5173`

### Build for Production

```bash
npm run build
```

This will generate static files in the `dist` directory.

## Features

- Vue 3 Composition API
- Element Plus UI Component Library
- Form validation
- JWT authentication
- Route guards for protected pages
- Responsive design

## API Configuration

The frontend expects the backend API to be running on `http://localhost:8080/api`.

Edit `vite.config.js` to change the proxy target if needed.

## Default Test Credentials

- **Admin**: `admin` / `admin123`
- **User**: `user` / `user123`

## Project Structure

```
frontend/
├── src/
│   ├── components/       # Vue components
│   ├── views/            # Page views (Login, Register, Dashboard)
│   ├── router/           # Vue Router configuration
│   ├── services/         # API services (authService)
│   ├── utils/            # Utilities (axios instance)
│   ├── assets/           # Images, fonts, etc.
│   ├── App.vue           # Root component
│   └── main.js           # Entry point
├── public/               # Static files
├── index.html            # HTML template
├── package.json
├── vite.config.js        # Vite configuration
└── README.md
```

## Available Routes

- `/login` - Login page (public)
- `/register` - Registration page (public)
- `/dashboard` - Dashboard page (protected)

## Authentication Flow

1. User login on `/login`
2. Backend returns JWT token
3. Token stored in localStorage
4. Token added to all subsequent requests in Authorization header
5. Route guard redirects to login if token is missing/expired

## Notes

- Change JWT_SECRET in production
- Use HTTPS in production
- Keep API_URL configurable via environment variables
- Never commit tokens or secrets
