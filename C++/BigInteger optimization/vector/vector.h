#pragma once
#ifndef VECTOR
#define VECTOR
#include <type_traits>
#include <memory>

template <class T>
struct vector {
	vector();
	vector(vector const& a);
	vector(size_t size, T);
	explicit vector(size_t size);
	~vector();

	friend bool operator==(vector const& a, vector const& b) {
		if (a.size() != b.size()) {
			return false;
		}
		for (size_t i = 0; i < a.size(); i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	vector& operator=(vector a);

	T& operator[](size_t ind);
	T const& operator[](size_t ind) const;

	T& front();
	T const& front() const;

	T& back();
	T const& back() const;

	bool empty() const;
	size_t size() const;

	size_t capacity() const;
	void reserve(size_t n);

	void push_back(T const& value);
	void pop_back();

	void swap(vector& a) noexcept;

private:
	size_t _size;
	std::shared_ptr<T> _data;
	size_t _capacity;

	static size_t get_new_capacity(size_t n);
	void set_capacity(size_t capacity);
	void delete_all();
	void change();
};

template <class T>
vector<T>::vector() {
	_data.reset();
	_size = 0;
	_capacity = 0;
}

template <class T>
vector<T>::vector(vector const& a) {
	_data = a._data;
	_size = a._size;
	_capacity = a._size;
}

template <class T>
vector<T>::vector(size_t size, T a) {
	_size = 0;
	_capacity = 0;
	reserve(size);
	for (size_t i = 0; i < size; i++) {
		push_back(a);
	}
}

template <class T>
vector<T>::vector(size_t size) : vector(size, T()) { }

template <class T>
vector<T>::~vector() {
	delete_all();
}

template <class T>
vector<T>& vector<T>::operator=(vector a) {
	swap(a);
	return *this;
}

template <class T>
T& vector<T>::operator[](size_t ind) {
	change();
	return _data.get()[ind];
}

template <class T>
T const& vector<T>::operator[](size_t ind) const {
	return _data.get()[ind];
}

template <class T>
T& vector<T>::front() {
	change();
	return _data.get()[0];
}

template <class T>
T const& vector<T>::front() const {
	return _data.get()[0];
}

template <class T>
T& vector<T>::back() {
	change();
	return _data.get()[_size - 1];
}

template <class T>
T const& vector<T>::back() const {
	return _data.get()[_size - 1];
}
template <class T>
bool vector<T>::empty() const {
	return _size == 0;
}

template <class T>
size_t vector<T>::size() const {
	return _size;
}

template <class T>
size_t vector<T>::capacity() const {
	return _capacity;
}

template <class T>
void vector<T>::reserve(size_t n) {
	change();
	if (_capacity < n) {
		set_capacity(get_new_capacity(n));
	}
}

template <class T>
void vector<T>::push_back(T const& value) {
	change();
	if (_size == _capacity) {
		size_t capacity = get_new_capacity(_size + 1);
		T* temp = copy_data(capacity, _data.get(), size());
		new(&temp[_size]) T(value);
		delete_all();
		_data.reset(temp);
		_capacity = capacity;
	}
	else {
		new(&(*this)[_size]) T(value);
	}
	_size++;
}

template <class T>
void vector<T>::pop_back() {
	change();
	_data.get()[_size - 1].~T();
	_size--;
}

template <class T>
void vector<T>::swap(vector& a) noexcept {
	using std::swap;
	swap(a._data, _data);
	swap(a._size, _size);
	swap(a._capacity, _capacity);
}

template <class T>
size_t vector<T>::get_new_capacity(size_t n) {
	if (n == 0) {
		return 8;
	}
	return n * 3 / 2;
}

template <class T>
void vector<T>::set_capacity(size_t capacity) {
	change();
	T* temp = copy_data(capacity, _data.get(), size());
	delete_all();
	_data.reset(temp);
	_capacity = capacity;
}

template <class T>
void vector<T>::delete_all() {
	if (_capacity > 0) {
		delete_data(_data.get(), _size);
	}
}

template <class T>
void vector<T>::change() {
	if(!_data.unique()) {
		_data.reset(copy_data(size(), _data.get(), size()));
	}
}

template <class T>
T* copy_data(size_t capacity, T* data, size_t size,
             typename std::enable_if<!std::is_trivially_constructible<T>::value>::type* = nullptr) {
	T* temp = static_cast<T*>(operator new(capacity * sizeof(T)));
	for (size_t i = 0; i < size; i++) {
		try {
			new(&temp[i]) T(data[i]);
		}
		catch (...) {
			delete_data(temp, i);
			operator delete(temp);
			throw;
		}
	}
	return temp;
}

template <class T>
T* copy_data(size_t capacity, T* data, size_t size,
             typename std::enable_if<std::is_trivially_constructible<T>::value>::type* = nullptr) {
	T* temp = static_cast<T*>(operator new(capacity * sizeof(T)));
	memcpy(temp, data, sizeof(T) * size);
	return temp;
}

template <class T>
void delete_data(T* data, size_t size,
                 typename std::enable_if<!std::is_trivially_destructible<T>::value>::type* = nullptr) {
	for (size_t j = 0; j < size; j++) {
		data[j].~T();
	}
}

template <class T>
void delete_data(T* data, size_t size,
                 typename std::enable_if<std::is_trivially_destructible<T>::value>::type* = nullptr) { }
#endif // VECTOR