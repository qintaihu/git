import api from '../utils/api'

class AuthService {
  async login(username, password) {
    try {
      const response = await api.post('/auth/login', {
        username,
        password
      })
      if (response.data.code === 200) {
        const { token } = response.data.data
        localStorage.setItem('token', token)
        return {
          success: true,
          message: 'Login successful',
          token
        }
      } else {
        return {
          success: false,
          message: response.data.message
        }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Login failed'
      }
    }
  }

  async register(username, password, email) {
    try {
      const response = await api.post('/auth/register', {
        username,
        password,
        email
      })
      if (response.data.code === 201) {
        return {
          success: true,
          message: 'Registration successful'
        }
      } else {
        return {
          success: false,
          message: response.data.message
        }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Registration failed'
      }
    }
  }

  logout() {
    localStorage.removeItem('token')
  }

  getToken() {
    return localStorage.getItem('token')
  }

  isAuthenticated() {
    return !!this.getToken()
  }

  async getCurrentUser() {
    try {
      const response = await api.get('/auth/me')
      if (response.data.code === 200) {
        return response.data.data
      }
      return null
    } catch (error) {
      return null
    }
  }
}

export default new AuthService()
