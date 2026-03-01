import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';

export default function ProductPage() {
  const { id } = useParams();
  const [product, setProduct] = useState(null);

  useEffect(() => { api.get(`/api/products/${id}`).then((r) => setProduct(r.data)); }, [id]);
  if (!product) return <div className="p-4">Loading...</div>;

  return <div className="max-w-4xl mx-auto p-4"><h2 className="text-2xl font-bold">{product.name}</h2><p>{product.description}</p></div>;
}
