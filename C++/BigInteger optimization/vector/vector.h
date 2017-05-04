#pragma once
#ifndef VECTOR
#define VECTOR
#include <type_traits>
#include <memory>

template <class T>
struct vector {
	vector();
	vector(vector const& a);
	vector(size_t size, T const& a);
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

	void reserve(size_t n);

	void push_back(T const& value);
	void pop_back();

	void swap(vector& a) noexcept;

private:
	static const size_t size_small = 8;

	size_t _size;
	bool _is_big;
	T& (vector::*_function_get)(size_t);
	T const& (vector::*_function_get_const)(size_t) const;

	struct small_big_type { // it can be union
		struct big_type {
			std::shared_ptr<T> _data;
			size_t _capacity;
		} big;

		struct small_type {
			T _data[size_small];
		} small;
	} _obj;

	T& get_small_ind(size_t ind);
	T& get_big_ind(size_t ind);
	T const& get_small_ind_c(size_t ind) const;
	T const& get_big_ind_c(size_t ind) const;

	static size_t get_new_capacity(size_t n);
	void set_capacity(size_t capacity);

	void delete_all();
	void change();

	bool is_big() const;
	void set_big_option();

	size_t get_capacity();
	T* get_data();
};

template <class T>
vector<T>::vector() :
	_size(0),
	_is_big(false),
	_function_get(&vector<T>::get_small_ind),
	_function_get_const(&vector<T>::get_small_ind_c) { }

template <class T>
vector<T>::vector(vector const& a) :
	_size(a.size()),
	_is_big(a._is_big),
	_function_get(a._function_get),
	_function_get_const(a._function_get_const),
	_obj(a._obj) { }

template <class T>
vector<T>::vector(size_t size, T const& a) : vector() {
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
	return (this ->* _function_get)(ind);
}

template <class T>
T const& vector<T>::operator[](size_t ind) const {
	return (this ->* _function_get_const)(ind);
}

template <class T>
T& vector<T>::front() {
	change();
	return (*this)[0];
}

template <class T>
T const& vector<T>::front() const {
	return (*this)[0];
}

template <class T>
T& vector<T>::back() {
	change();
	return (*this)[size() - 1];
}

template <class T>
T const& vector<T>::back() const {
	return (*this)[size() - 1];
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
void vector<T>::reserve(size_t n) {
	if (get_capacity() < n) {
		set_capacity(get_new_capacity(n));
	}
}

template <class T>
void vector<T>::push_back(T const& value) {
	if (size() == get_capacity()) {
		size_t capacity = get_new_capacity(size() + 1);
		T* temp = copy_data(capacity, get_data(), size());
		new(&temp[size()]) T(value);
		delete_all();
		_obj.big._data.reset(temp);
		_obj.big._capacity = capacity;
		if (!is_big()) {
			set_big_option();
		}
	}
	else {
		change();
		new(&(*this)[size()]) T(value);
	}
	_size++;
}

template <class T>
void vector<T>::pop_back() {
	if (is_big()) {
		change();
	}
	(*this)[size()].~T();
	_size--;
}

template <class T>
void vector<T>::swap(vector& a) noexcept {
	using std::swap;
	swap(a._obj, _obj);
	swap(a._size, _size);
	swap(a._is_big, _is_big);
	swap(a._function_get, _function_get);
	swap(a._function_get_const, _function_get_const);
}

template <class T>
T& vector<T>::get_small_ind(size_t ind) {
	return _obj.small._data[ind];
}

template <class T>
T& vector<T>::get_big_ind(size_t ind) {
	change();
	return _obj.big._data.get()[ind];
}

template <class T>
T const& vector<T>::get_small_ind_c(size_t ind) const {
	return _obj.small._data[ind];
}

template <class T>
T const& vector<T>::get_big_ind_c(size_t ind) const {
	return _obj.big._data.get()[ind];
}

template <class T>
size_t vector<T>::get_new_capacity(size_t n) {
	if (n == 0) {
		return 4;
	}
	return n * 3 / 2;
}

template <class T>
void vector<T>::set_capacity(size_t capacity) {
	if (is_big() || capacity > size_small) {
		T* temp = copy_data(capacity, get_data(), size());
		delete_all();
		_obj.big._data.reset(temp);
		_obj.big._capacity = capacity;
		if(!is_big()) {
			set_big_option();
		}
	}
}

template <class T>
void vector<T>::delete_all() {
	delete_data(get_data(), size());
}

template <class T>
void vector<T>::change() {
	if (is_big() && !_obj.big._data.unique()) {
		_obj.big._data.reset(copy_data(size(), _obj.big._data.get(), size()));
	}
}

template <class T>
bool vector<T>::is_big() const {
	return _is_big;
}

template <class T>
void vector<T>::set_big_option() {
	_is_big = true;
	_function_get = &vector<T>::get_big_ind;
	_function_get_const = &vector<T>::get_big_ind_c;
}

template <class T>
size_t vector<T>::get_capacity() {
	if (is_big()) {
		return _obj.big._capacity;
	}
	return size_small;
}

template <class T>
T* vector<T>::get_data() {
	if (is_big()) {
		return _obj.big._data.get();
	}
	return _obj.small._data;
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