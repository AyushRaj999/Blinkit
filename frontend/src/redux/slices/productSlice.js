import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import api from '../../services/api';

export const fetchProducts = createAsyncThunk('products/fetch', async (q = '') => {
  const { data } = await api.get('/api/products', { params: { q, size: 20 } });
  return data.content;
});

const productSlice = createSlice({
  name: 'products',
  initialState: { items: [], loading: false },
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(fetchProducts.pending, (s) => { s.loading = true; });
    builder.addCase(fetchProducts.fulfilled, (s, a) => { s.loading = false; s.items = a.payload; });
    builder.addCase(fetchProducts.rejected, (s) => { s.loading = false; });
  }
});

export default productSlice.reducer;
